package br.com.votingsessionmanager.votingsessionmanager.application.votingsession.request;

/**
 * The class {@code OpenVotingSessionAgendaResponse} is used to populated when application
 * send response to indicate that identifier of agenda on voting session that are been opened 
 */
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
