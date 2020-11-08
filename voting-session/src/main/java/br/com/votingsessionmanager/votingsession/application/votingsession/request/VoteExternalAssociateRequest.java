package br.com.votingsessionmanager.votingsession.application.votingsession.request;

import static br.com.votingsessionmanager.votingsession.domain.agenda.VoteType.NO;
import static br.com.votingsessionmanager.votingsession.domain.agenda.VoteType.YES;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.votingsessionmanager.votingsession.application.votingsession.validator.VoteTypeSubSet;
import br.com.votingsessionmanager.votingsession.domain.agenda.VoteType;

/**
 * The class {@code VoteExternalAssociateRequest} is populated when application receive request
 * to save the vote on agenda present on voting session
 */
public class VoteExternalAssociateRequest implements GenericVoteRequest {

	@NotNull
	@NotBlank
	@JsonProperty("associate_id")
	private String associateId;

	@NotNull
	@Positive
	@JsonProperty("agenda_id")
	private Long agendaId;

	@VoteTypeSubSet(anyOf = { YES, NO })
	private VoteType vote;

	@SuppressWarnings("unused")
	private VoteExternalAssociateRequest() {

	}

	public VoteExternalAssociateRequest(@NotNull @NotBlank String associateId, @NotNull @Positive Long agendaId,
			@VoteTypeSubSet(anyOf = { YES, NO }) VoteType vote) {
		this.associateId = associateId;
		this.agendaId = agendaId;
		this.vote = vote;
	}

	@Override
	public String getAssociateId() {
		return associateId;
	}

	@Override
	public Long getAgendaId() {
		return agendaId;
	}

	@Override
	public VoteType getVote() {
		return vote;
	}

}
