package br.com.votingsessionmanager.votingsessionmanager.application.agenda.exception;

import br.com.votingsessionmanager.commons.application.exception.InvalidResourceReferenceException;

public class InvalidAgendaResourceReferenceException extends InvalidResourceReferenceException {

	private Long id;

	private static final long serialVersionUID = 1L;

	public InvalidAgendaResourceReferenceException(Long id) {
		this.id = id;
	}

	@Override
	public String getResource() {
		return "agenda_id";
	}

	@Override
	public Long getId() {
		return id;
	}

}
