package br.com.votingsessionmanager.votingsession.application.votingsession.request;

import static br.com.votingsessionmanager.votingsession.domain.agenda.VoteType.NO;
import static br.com.votingsessionmanager.votingsession.domain.agenda.VoteType.YES;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.votingsessionmanager.votingsession.application.votingsession.validator.VoteTypeSubSet;
import br.com.votingsessionmanager.votingsession.domain.agenda.VoteType;

/**
 * The class {@code VoteRequest} is populated when application
 * receive request to save the vote on agenda present on voting session 
 */
public class VoteRequest {

	@NotNull
	@Positive
	@JsonProperty("associate_id")
	private Long associateId;

	@NotNull
	@Positive
	@JsonProperty("agenda_id")
	private Long agendaId;

	@VoteTypeSubSet(anyOf = { YES, NO })
	private VoteType vote;

	@SuppressWarnings("unused")
	private VoteRequest() {

	}

	public VoteRequest(@NotNull @Positive Long associateId, @NotNull @Positive Long agendaId,
			@VoteTypeSubSet(anyOf = { YES, NO }) VoteType vote) {
		this.associateId = associateId;
		this.agendaId = agendaId;
		this.vote = vote;
	}

	public Long getAssociateId() {
		return associateId;
	}

	public Long getAgendaId() {
		return agendaId;
	}

	public VoteType getVote() {
		return vote;
	}

}
