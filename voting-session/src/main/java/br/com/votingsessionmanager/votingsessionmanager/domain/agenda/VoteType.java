package br.com.votingsessionmanager.votingsessionmanager.domain.agenda;

public enum VoteType {
	YES,
	NO;

	public boolean isInFavor() {
		return this.equals(YES);
	}
	
	public boolean isAgainst() {
		return this.equals(NO);
	}

}
