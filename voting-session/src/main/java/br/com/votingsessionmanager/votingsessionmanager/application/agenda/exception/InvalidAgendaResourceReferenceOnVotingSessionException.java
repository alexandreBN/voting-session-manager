package br.com.votingsessionmanager.votingsessionmanager.application.agenda.exception;

import br.com.votingsessionmanager.commons.application.exception.InvalidResourceForeignKeyReferenceOnResourceException;


/**
 * The class {@code InvalidAgendaResourceReferenceOnVotingSessionException} is a form of
 * {@code Throwable} that indicates that foreign agenda resource referenced on voting session resource aren't present and this 
 * conditions that a reasonable application might want to catch.
 */
public class InvalidAgendaResourceReferenceOnVotingSessionException extends InvalidResourceForeignKeyReferenceOnResourceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long agendaId;

	public InvalidAgendaResourceReferenceOnVotingSessionException(Long agendaId) {
		this.agendaId = agendaId;
	}

	@Override
	public String getMainResource() {
		return "voting_session";
	}

	@Override
	public String getForeignResource() {
		return "agenda_id";
	}

	@Override
	public Long getForeignKeyResourceId() {
		return agendaId;
	}

}
