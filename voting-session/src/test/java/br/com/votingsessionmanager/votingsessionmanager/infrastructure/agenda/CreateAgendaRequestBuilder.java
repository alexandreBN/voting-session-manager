package br.com.votingsessionmanager.votingsessionmanager.infrastructure.agenda;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.votingsessionmanager.votingsession.application.agenda.request.CreateAgendaRequest;
import br.com.votingsessionmanager.votingsessionmanager.converter.JSONObjectToStringBuilderConverter;

public class CreateAgendaRequestBuilder extends JSONObjectToStringBuilderConverter {

	private String name;
	private String description;

	public CreateAgendaRequestBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public CreateAgendaRequestBuilder withDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public String buildJSONString() throws JsonProcessingException {
		CreateAgendaRequest agendaRequest = new CreateAgendaRequest(name, description);
		String agendaRequestAsString = convert(agendaRequest);
		return agendaRequestAsString;
	}

}
