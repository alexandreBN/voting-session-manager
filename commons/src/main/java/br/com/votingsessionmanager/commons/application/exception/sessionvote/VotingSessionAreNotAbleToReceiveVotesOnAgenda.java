package br.com.votingsessionmanager.commons.application.exception.sessionvote;

public class VotingSessionAreNotAbleToReceiveVotesOnAgenda extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long agendaId;

	public VotingSessionAreNotAbleToReceiveVotesOnAgenda(Long agendaId) {
		this.agendaId = agendaId;
	}

	public Long getAgendaId() {
		return agendaId;
	}

}
