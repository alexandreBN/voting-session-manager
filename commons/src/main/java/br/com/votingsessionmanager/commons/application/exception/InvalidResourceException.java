package br.com.votingsessionmanager.commons.application.exception;

/**
 * The abstract class {@code InvalidResourceException} is a form of {@code Throwable} that indicates
 * that resource referenced (defined on the another subclasses) aren't present and this condition 
 * can be reasonable to application might want to catch.
 */
public abstract class InvalidResourceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract String getResource();

	public abstract <T> T getId();

}
