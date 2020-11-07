package br.com.votingsessionmanager.votingsessionmanager.application.votingsession.request;

public class OpenVotingSessionAgendaResponse {
	private Long id;

	@SuppressWarnings("unused")
	private OpenVotingSessionAgendaResponse() {
		
	}

	public OpenVotingSessionAgendaResponse(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
