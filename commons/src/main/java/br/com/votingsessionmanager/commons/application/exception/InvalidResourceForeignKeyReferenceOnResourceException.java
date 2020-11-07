package br.com.votingsessionmanager.commons.application.exception;

public abstract class InvalidResourceForeignKeyReferenceOnResourceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public abstract String getMainResource();
	public abstract String getForeignResource();
	public abstract Long getForeignKeyResourceId();

}
