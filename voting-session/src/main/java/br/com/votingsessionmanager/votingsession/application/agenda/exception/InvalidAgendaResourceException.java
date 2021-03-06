package br.com.votingsessionmanager.votingsession.application.agenda.exception;

import br.com.votingsessionmanager.commons.application.exception.InvalidResourceException;

/**
 * The class {@code InvalidAgendaResourceException} is a form of {@code Throwable}
 * that indicates that agenda resource referenced isn't present and this condition 
 * can be reasonable to application might want to catch.
 */
public class InvalidAgendaResourceException extends InvalidResourceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String resource;
	private final Long id;

	public InvalidAgendaResourceException(Long id) {
		this.resource = "product";
		this.id = id;
	}

	@Override
	public String getResource() {
		return resource;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long getId() {
		return id;
	}

}
