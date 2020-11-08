package br.com.votingsessionmanager.commons.application.exception;

/**
 * The abstract class {@code InvalidResourceReferenceException} is a form of {@code Throwable} that indicates
 * that resource referenced (defined on the another subclasses) aren't present and this condition 
 * can be reasonable to application might want to catch.
 */
public abstract class InvalidResourceReferenceException extends InvalidResourceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
