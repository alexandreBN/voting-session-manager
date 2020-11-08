package br.com.votingsessionmanager.votingsession.infrastructure.votingsession;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.votingsessionmanager.votingsession.application.votingsession.request.VoteRequest;
import br.com.votingsessionmanager.votingsession.converter.JSONObjectToStringBuilderConverter;
import br.com.votingsessionmanager.votingsession.domain.agenda.VoteType;

public class CreateVoteRequestBuilder<T> extends JSONObjectToStringBuilderConverter {

	private T associateId;
	private Long agendaId;
	private VoteType vote;

	public CreateVoteRequestBuilder withAssociateId(T associateId) {
		this.associateId = associateId;
		return this;
	}

	public CreateVoteRequestBuilder withAgendaId(Long agendaId) {
		this.agendaId = agendaId;
		return this;
	}

	public CreateVoteRequestBuilder withVote(VoteType vote) {
		this.vote = vote;
		return this;
	}

	@Override
	public String buildJSONString() throws JsonProcessingException {
		String identifier = String.valueOf(associateId);
		VoteRequest voteRequest = new VoteRequest(identifier, agendaId, vote);
		String voteRequestAsString = convert(voteRequest);
		return voteRequestAsString;
	}

}
