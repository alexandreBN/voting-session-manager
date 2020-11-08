package br.com.votingsessionmanager.votingsession.application.votingsession.request;

import br.com.votingsessionmanager.votingsession.domain.associate.AssociateVoteStatusType;

/**
 * The class {@code FindExternalAssociateVoteStatusResponse} is populated when application receive response
 * to external application that informed if user is unable vote
 */
public class FindExternalAssociateVoteStatusResponse {
	private AssociateVoteStatusType status;

	public AssociateVoteStatusType getStatus() {
		return status;
	}

}
