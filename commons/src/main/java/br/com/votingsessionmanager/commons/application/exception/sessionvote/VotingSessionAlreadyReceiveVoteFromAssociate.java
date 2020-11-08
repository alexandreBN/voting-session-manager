package br.com.votingsessionmanager.commons.application.exception.sessionvote;

/**
 * The class {@code VotingSessionAlreadyReceiveVoteFromAssociate} indicate
 * that voting session resource already receive votes from associate referenced
 * and this condition can be reasonable to application might want to catch.
 */
public class VotingSessionAlreadyReceiveVoteFromAssociate extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long associateId;

	public VotingSessionAlreadyReceiveVoteFromAssociate(Long associateId) {
		this.associateId = associateId;
	}

	public Long getAssociateId() {
		return associateId;
	}

}
