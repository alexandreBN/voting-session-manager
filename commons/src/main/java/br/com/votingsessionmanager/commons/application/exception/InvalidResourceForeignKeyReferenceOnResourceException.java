package br.com.votingsessionmanager.commons.application.exception;

/**
 * The abstract class {@code InvalidResourceForeignKeyReferenceOnResourceException} and its subclasses are a form of
 * {@code Throwable} that indicates that foreign resource referenced on main resource aren't present and this 
 * conditions that a reasonable application might want to catch.
 */
public abstract class InvalidResourceForeignKeyReferenceOnResourceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract String getMainResource();

	public abstract String getForeignResource();

	public abstract Long getForeignKeyResourceId();

}
