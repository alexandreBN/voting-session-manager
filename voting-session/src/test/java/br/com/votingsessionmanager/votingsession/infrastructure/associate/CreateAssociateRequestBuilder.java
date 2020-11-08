package br.com.votingsessionmanager.votingsession.infrastructure.associate;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.votingsessionmanager.votingsession.application.associate.request.CreateAssociateRequest;
import br.com.votingsessionmanager.votingsession.converter.JSONObjectToStringBuilderConverter;

public class CreateAssociateRequestBuilder extends JSONObjectToStringBuilderConverter {

	private String name;

	public CreateAssociateRequestBuilder withName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String buildJSONString() throws JsonProcessingException {
		CreateAssociateRequest associateRequest = new CreateAssociateRequest(name);
		String agendaRequestAsString = convert(associateRequest);
		return agendaRequestAsString;
	}

}
