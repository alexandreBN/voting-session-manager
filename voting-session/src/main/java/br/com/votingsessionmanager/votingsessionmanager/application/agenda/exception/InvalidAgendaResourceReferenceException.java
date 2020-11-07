package br.com.votingsessionmanager.votingsessionmanager.application.agenda.exception;

public class InvalidAgendaResourceReferenceException extends InvalidAgendaResourceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidAgendaResourceReferenceException(Long id) {
		super(id);
	}

}
