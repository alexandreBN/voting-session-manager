package br.com.votingsessionmanager.votingsessionmanager.application.votingsession.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The class {@code OpenVotingSessionResponse} is populated when application
 * send response to indicate that data of voting session that are been opened 
 */
public class OpenVotingSessionResponse {

	private Long id;
	private OpenVotingSessionAgendaResponse agenda;

	@JsonProperty("open_until")
	private LocalDateTime openUntil;

	@SuppressWarnings("unused")
	private OpenVotingSessionResponse() {
	}

	public OpenVotingSessionResponse(Long id, Long agendaId, LocalDateTime openUntil) {
		this.id = id;
		this.agenda = new OpenVotingSessionAgendaResponse(agendaId);
		this.openUntil = openUntil;
	}

	public Long getId() {
		return id;
	}

	public OpenVotingSessionAgendaResponse getAgenda() {
		return agenda;
	}

	public LocalDateTime getOpenUntil() {
		return openUntil;
	}

}
