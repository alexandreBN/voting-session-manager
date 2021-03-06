package br.com.votingsessionmanager.votingsession.application.associate.exception;

import br.com.votingsessionmanager.commons.application.exception.InvalidResourceReferenceException;

/**
 * The class {@code InvalidAssociateResourceReferenceException} is a form of {@code Throwable}
 * that indicates that referenced associate resource isn't present and this condition 
 * can be reasonable to application might want to catch.
 */
public class InvalidAssociateResourceReferenceException extends InvalidResourceReferenceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	public InvalidAssociateResourceReferenceException(String id) {
		this.id = id;
	}

	@Override
	public String getResource() {
		return "associate_id";
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getId() {
		return this.id;
	}

}
