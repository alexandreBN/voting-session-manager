package br.com.votingsessionmanager.votingsessionmanager.domain.votingsession;

import java.time.LocalDateTime;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.votingsessionmanager.votingsessionmanager.domain.agenda.VoteType;

public class VotingSessionResult {

	private String agenda;

	@JsonProperty("total_votes")
	private Integer totalVotes;

	private HashMap<VoteType, Integer> votes;

	@JsonProperty("open_until")
	private LocalDateTime openUntil;

	public VotingSessionResult(String agenda, Integer totalVotes, Integer totalVotesInFavor, Integer totalVotesAgainst, LocalDateTime openUntil) {
		this.agenda = agenda;
		this.totalVotes = totalVotes;
		votes = new HashMap<>();
		votes.put(VoteType.YES, totalVotesInFavor);
		votes.put(VoteType.NO, totalVotesAgainst);
		this.openUntil = openUntil;
	}

	public String getAgenda() {
		return agenda;
	}

	public Integer getTotalVotes() {
		return totalVotes;
	}

	public LocalDateTime getOpenUntil() {
		return openUntil;
	}

	public HashMap<VoteType, Integer> getVotes() {
		return votes;
	}

}
