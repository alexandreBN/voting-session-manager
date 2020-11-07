package br.com.votingsessionmanager.votingsessionmanager.application.votingsession.request;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VotingSessionRequest {

	@NotNull
	@Positive
	@JsonProperty("agenda_id")
	private Long agendaId;

	@Valid
	@JsonProperty("open_until")
	private VotingSessionTimeLimitRequest openUntil;

	public Long getAgendaId() {
		return agendaId;
	}

	public VotingSessionTimeLimitRequest getOpenUntil() {
		return openUntil;
	}

	public LocalDateTime duration() {
		if(openUntil == null) return LocalDateTime.now().plusMinutes(1);
		return openUntil.duration();
	}

}
