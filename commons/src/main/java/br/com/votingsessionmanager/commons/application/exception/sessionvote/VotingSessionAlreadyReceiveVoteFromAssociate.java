package br.com.votingsessionmanager.commons.application.exception.sessionvote;

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
