package br.com.votingsessionmanager.votingsessionmanager.application.associate.exception;

import br.com.votingsessionmanager.commons.application.exception.InvalidResourceException;

/**
 * The class {@code InvalidAssociateResourceException} is a form of {@code Throwable}
 * that indicates that associate resource referenced isn't present and this condition 
 * can be reasonable to application might want to catch.
 */
public class InvalidAssociateResourceException extends InvalidResourceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String resource;
	private final Long id;

	public InvalidAssociateResourceException(Long id) {
		this.resource = "associate";
		this.id = id;
	}

	@Override
	public String getResource() {
		return resource;
	}

	@Override
	public Long getId() {
		return id;
	}

}
