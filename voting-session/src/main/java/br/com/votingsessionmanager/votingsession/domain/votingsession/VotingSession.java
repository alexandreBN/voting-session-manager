package br.com.votingsessionmanager.votingsession.domain.votingsession;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.votingsessionmanager.votingsession.domain.agenda.Agenda;
import br.com.votingsessionmanager.votingsession.domain.agenda.Vote;

@Entity
public class VotingSession {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "agenda_id")
	private Agenda agenda;

	@Column(name = "open_until")
	private LocalDateTime openUntil;

	@SuppressWarnings("unused")
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

	/**
	 * Return condition whether associate with specified id already been vote on this agenda
	 * 
	 * @param associateId associate identifier
	 * @return boolean value that indicate if associate with specified id already been vote on this agenda
	 */
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

	/**
	 * Return voting session result data, it is a agenda name and total
	 * number of votes (and on each vote category)
	 * 
	 * @return voting session result data
	 */
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
