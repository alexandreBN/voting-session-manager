package br.com.votingsessionmanager.commons.application.exception;

public abstract class InvalidResourceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract String getResource();

	public abstract Long getId();

}
