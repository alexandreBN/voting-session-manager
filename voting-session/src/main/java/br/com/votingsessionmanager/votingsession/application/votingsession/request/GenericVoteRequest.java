package br.com.votingsessionmanager.votingsession.application.votingsession.request;

import br.com.votingsessionmanager.votingsession.domain.agenda.VoteType;

/**
 * The interface class {@code GenericVoteRequest} can be used implemented by
 * vote request classes used to register one vote on agenda
 */
public interface GenericVoteRequest {
	public <T> T getAssociateId();

	public Long getAgendaId();

	public VoteType getVote();

}
