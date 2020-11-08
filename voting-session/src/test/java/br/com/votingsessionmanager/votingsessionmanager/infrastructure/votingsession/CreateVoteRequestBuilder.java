package br.com.votingsessionmanager.votingsessionmanager.infrastructure.votingsession;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.votingsessionmanager.votingsession.application.votingsession.request.VoteRequest;
import br.com.votingsessionmanager.votingsession.domain.agenda.VoteType;
import br.com.votingsessionmanager.votingsessionmanager.converter.JSONObjectToStringBuilderConverter;

public class CreateVoteRequestBuilder extends JSONObjectToStringBuilderConverter {

	private Long associateId;
	private Long agendaId;
	private VoteType vote;

	public CreateVoteRequestBuilder withAssociateId(Long associateId) {
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
		VoteRequest voteRequest = new VoteRequest(associateId, agendaId, vote);
		String voteRequestAsString = convert(voteRequest);
		return voteRequestAsString;
	}

}
