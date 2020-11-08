package br.com.votingsessionmanager.votingsession.application.associate;

import java.util.Optional;

import br.com.votingsessionmanager.votingsession.domain.associate.Associate;

/**
 * The abstract class {@code SearchAssociate} a strategy class to be implemented
 * to find associates based on identifier 
 */
public abstract class FindAssociate {

	public abstract <T> Optional<Associate> find(T associateIdentifier);

	public abstract <T> void throwErrorIfAssociateIsNotAbleToVote(T associateIdentifier) throws Exception;
	
	public void teste() throws Exception {
		new Exception();
	}

}
