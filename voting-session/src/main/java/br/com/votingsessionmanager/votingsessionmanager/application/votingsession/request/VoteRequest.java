package br.com.votingsessionmanager.votingsessionmanager.application.votingsession.request;

import static br.com.votingsessionmanager.votingsessionmanager.domain.agenda.VoteType.NO;
import static br.com.votingsessionmanager.votingsessionmanager.domain.agenda.VoteType.YES;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.votingsessionmanager.votingsessionmanager.application.votingsession.validator.VoteTypeSubSet;
import br.com.votingsessionmanager.votingsessionmanager.domain.agenda.VoteType;

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
