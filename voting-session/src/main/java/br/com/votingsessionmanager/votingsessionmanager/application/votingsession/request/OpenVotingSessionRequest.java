package br.com.votingsessionmanager.votingsessionmanager.application.votingsession.request;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The class {@code OpenVotingSessionRequest} is populated when application
 * receive request to open a voting session based on agenda 
 */
public class OpenVotingSessionRequest {

	@NotNull
	@Positive
	@JsonProperty("agenda_id")
	private Long agendaId;

	@Valid
	@JsonProperty("open_until")
	private OpenVotingSessionTimeLimitRequest openUntil;

	@SuppressWarnings("unused")
	private OpenVotingSessionRequest() { }

	public OpenVotingSessionRequest(@NotNull @Positive Long agendaId) {
		this.agendaId = agendaId;
	}

	public OpenVotingSessionRequest(@NotNull @Positive Long agendaId,
			@Valid OpenVotingSessionTimeLimitRequest openUntil) {
		this.agendaId = agendaId;
		this.openUntil = openUntil;
	}

	public Long getAgendaId() {
		return agendaId;
	}

	public OpenVotingSessionTimeLimitRequest getOpenUntil() {
		return openUntil;
	}

	/**
	 * Return the duration time of voting session based
	 * If open_until field value aren't defined, the default value is current time plus 1 minute
	 * However if open_until is not null this value is used on voting session duration time
	 * @return voting session duration time
	 */
	public LocalDateTime duration() {
		if (openUntil == null || openUntil.isNotDefinedValues()) return LocalDateTime.now().plusMinutes(1);
		return openUntil.duration();
	}

}
