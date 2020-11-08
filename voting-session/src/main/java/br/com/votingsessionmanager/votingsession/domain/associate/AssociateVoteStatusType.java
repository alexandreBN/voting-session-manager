package br.com.votingsessionmanager.votingsession.domain.associate;

public enum AssociateVoteStatusType {
	ABLE_TO_VOTE,
	UNABLE_TO_VOTE;

	public boolean isUnableToVote() {
		return this.equals(UNABLE_TO_VOTE);
	}

}
