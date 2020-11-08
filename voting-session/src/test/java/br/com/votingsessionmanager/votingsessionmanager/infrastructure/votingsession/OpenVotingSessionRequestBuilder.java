package br.com.votingsessionmanager.votingsessionmanager.infrastructure.votingsession;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.votingsessionmanager.votingsession.application.votingsession.request.OpenVotingSessionRequest;
import br.com.votingsessionmanager.votingsession.application.votingsession.request.OpenVotingSessionTimeLimitRequest;
import br.com.votingsessionmanager.votingsessionmanager.converter.JSONObjectToStringBuilderConverter;

public class OpenVotingSessionRequestBuilder extends JSONObjectToStringBuilderConverter {

	private Long agendaId;
	private Integer days;
	private Integer hours;
	private Integer minutes;

	public OpenVotingSessionRequestBuilder withAgendaId(Long agendaId) {
		this.agendaId = agendaId;
		return this;
	}
	
	public OpenVotingSessionRequestBuilder withOpenedToVotesOnMaxDays(Integer days) {
		this.days = days;
		return this;
	}
	
	public OpenVotingSessionRequestBuilder withOpenedToVotesOnMaxHours(Integer hours) {
		this.hours = hours;
		return this;
	}

	public OpenVotingSessionRequestBuilder withOpenedToVotesOnMaxMinutes(Integer minutes) {
		this.minutes = minutes;
		return this;
	}
	
	protected boolean containsTimeLimit() {
		return days == null && hours == null && minutes == null;
	}

	@Override
	public String buildJSONString() throws JsonProcessingException {
		OpenVotingSessionRequest request = null;
		
		if(containsTimeLimit()) {
			request = new OpenVotingSessionRequest(agendaId);
		} else {
			request = new OpenVotingSessionRequest(agendaId, new OpenVotingSessionTimeLimitRequest(days, hours, minutes));
		}

		String requestAsString = convert(request);
		return requestAsString;
	}

}
