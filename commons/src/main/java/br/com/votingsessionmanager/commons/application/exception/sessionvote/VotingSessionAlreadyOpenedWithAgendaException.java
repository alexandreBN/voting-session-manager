package br.com.votingsessionmanager.commons.application.exception.sessionvote;

/**
 * The class {@code VotingSessionAlreadyOpenedWithAgendaException} indicate
 * that voting session resource referenced (based on agenda identifer) is already opened and this condition 
 * can be reasonable to application might want to catch.
 */
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
