package br.com.votingsessionmanager.votingsessionmanager.application.associate.exception;

import br.com.votingsessionmanager.commons.application.exception.InvalidResourceReferenceException;

public class InvalidAssociateResourceReferenceException extends InvalidResourceReferenceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	public InvalidAssociateResourceReferenceException(Long id) {
		this.id = id;
	}

	@Override
	public String getResource() {
		return "associate_id";
	}

	@Override
	public Long getId() {
		return this.id;
	}

}
