package br.com.votingsessionmanager.commons.application.exception.sessionvote;

/**
 * The class {@code VotingSessionAreNotAbleToReceiveVotesOnAgenda} indicate
 * that voting session resource can't receive more votes because the duration time is expired
 * and this condition can be reasonable to application might want to catch.
 */
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
