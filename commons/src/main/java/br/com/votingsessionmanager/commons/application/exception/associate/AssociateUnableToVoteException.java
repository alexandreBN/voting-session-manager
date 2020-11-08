package br.com.votingsessionmanager.commons.application.exception.associate;

/**
 * The class {@code AssociateUnableToVoteException} is a form of
 * {@code Throwable} that indicates that associate is not enable to vote and
 * this condition can be reasonable to application might want to catch.
 */
public class AssociateUnableToVoteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String associateId;

	public AssociateUnableToVoteException(String associateId) {
		this.associateId = associateId;
	}

	public String getAssociateId() {
		return associateId;
	}

}
