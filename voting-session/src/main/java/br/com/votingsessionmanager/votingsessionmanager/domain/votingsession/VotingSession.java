package br.com.votingsessionmanager.votingsessionmanager.domain.votingsession;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.votingsessionmanager.votingsessionmanager.domain.agenda.Agenda;
import br.com.votingsessionmanager.votingsessionmanager.domain.agenda.Vote;

@Entity
public class VotingSession {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "agenda_id")
	private Agenda agenda;

	@Column(name = "open_until")
	private LocalDateTime openUntil;

	private VotingSession() {
	}

	public VotingSession(Agenda agenda, LocalDateTime localDateTime) {
		this.agenda = agenda;
		this.openUntil = localDateTime;
	}

	public Long getId() {
		return id;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public LocalDateTime getOpenUntil() {
		return openUntil;
	}

	public boolean canReceiveVotes() {
		LocalDateTime now = LocalDateTime.now();
		boolean canReceiveVotes = now.isBefore(openUntil);
		return canReceiveVotes;
	}

	public boolean alreadyReceiveVotesAssociate(Long associateId) {
		int numberOfCandidateVotes = agenda.getVotes().stream()
			.filter(vote -> vote.getAssociate().getId().equals(associateId))
			.collect(Collectors.toSet())
			.size();

		return numberOfCandidateVotes > 0;
	}

	public void add(Vote vote) {
		this.agenda.getVotes().add(vote);
	}

	public VotingSessionResult getResult() {
		int totalVotes = agenda.getVotes().size();
		int totalVotesInFavor = agenda.getVotes().stream()
			.filter(vote -> vote.getType().isInFavor())
			.collect(Collectors.toSet())
			.size();
		int totalVotesAgainst = totalVotes - totalVotesInFavor;

		VotingSessionResult result = new VotingSessionResult(agenda.getName(), totalVotes, totalVotesInFavor, totalVotesAgainst, openUntil);
		return result;
	}
}
