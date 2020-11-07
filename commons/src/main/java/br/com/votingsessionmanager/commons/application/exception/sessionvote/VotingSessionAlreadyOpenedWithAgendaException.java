package br.com.votingsessionmanager.commons.application.exception.sessionvote;

public class VotingSessionAlreadyOpenedWithAgendaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long agendaId;

	public VotingSessionAlreadyOpenedWithAgendaException(Long agendaId) {
		this.agendaId = agendaId;
	}

	public Long getAgendaId() {
		return agendaId;
	}

}
