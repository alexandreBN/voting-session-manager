package br.com.votingsessionmanager.votingsessionmanager.application.votingsession.request;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenVotingSessionRequest {

	@NotNull
	@Positive
	@JsonProperty("agenda_id")
	private Long agendaId;

	@Valid
	@JsonProperty("open_until")
	private OpenVotingSessionTimeLimitRequest openUntil;

	@SuppressWarnings("unused")
	private OpenVotingSessionRequest() { }

	public OpenVotingSessionRequest(@NotNull @Positive Long agendaId) {
		this.agendaId = agendaId;
	}

	public OpenVotingSessionRequest(@NotNull @Positive Long agendaId,
			@Valid OpenVotingSessionTimeLimitRequest openUntil) {
		this.agendaId = agendaId;
		this.openUntil = openUntil;
	}

	public Long getAgendaId() {
		return agendaId;
	}

	public OpenVotingSessionTimeLimitRequest getOpenUntil() {
		return openUntil;
	}

	public LocalDateTime duration() {
		if (openUntil == null || openUntil.isNotDefinedValues()) return LocalDateTime.now().plusMinutes(1);
		return openUntil.duration();
	}

}
