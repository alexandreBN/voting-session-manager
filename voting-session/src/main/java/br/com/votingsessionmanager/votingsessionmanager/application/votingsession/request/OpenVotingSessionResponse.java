package br.com.votingsessionmanager.votingsessionmanager.application.votingsession.request;

import java.time.LocalDateTime;

public class OpenVotingSessionResponse {

	private Long id;
	private OpenVotingSessionAgendaResponse agenda;
	private LocalDateTime openUntil;

	public OpenVotingSessionResponse(Long id, Long agendaId, LocalDateTime openUntil) {
		this.id = id;
		this.agenda = new OpenVotingSessionAgendaResponse(agendaId);
		this.openUntil = openUntil;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OpenVotingSessionAgendaResponse getAgenda() {
		return agenda;
	}

	public void setAgenda(OpenVotingSessionAgendaResponse agenda) {
		this.agenda = agenda;
	}

	public LocalDateTime getOpenUntil() {
		return openUntil;
	}

	public void setOpenUntil(LocalDateTime openUntil) {
		this.openUntil = openUntil;
	}

}
