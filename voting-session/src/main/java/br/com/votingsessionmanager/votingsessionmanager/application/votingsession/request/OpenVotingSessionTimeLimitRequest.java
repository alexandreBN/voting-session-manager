package br.com.votingsessionmanager.votingsessionmanager.application.votingsession.request;

import java.time.LocalDateTime;

import javax.validation.constraints.PositiveOrZero;

/**
 * The class {@code OpenVotingSessionTimeLimitRequest} is used to populated when application
 * receive request to open a voting session (and define open_until field) based on agenda 
 */
public class OpenVotingSessionTimeLimitRequest {

	@PositiveOrZero
	private Integer days = 0;

	@PositiveOrZero
	private Integer hours = 0;

	@PositiveOrZero
	private Integer minutes = 0;

	@SuppressWarnings("unused")
	private OpenVotingSessionTimeLimitRequest() {

	}

	public OpenVotingSessionTimeLimitRequest(@PositiveOrZero Integer days, @PositiveOrZero Integer hours,
			@PositiveOrZero Integer minutes) {
		this.days = days;
		this.hours = hours;
		this.minutes = minutes;
	}

	public long getDays() {
		return days;
	}

	public long getHours() {
		return hours;
	}

	public long getMinutes() {
		return minutes;
	}

	/**
	 * Return the duration time of voting session based
	 * @return voting session duration time
	 */
	public LocalDateTime duration() {
		return LocalDateTime.now().plusDays(days).plusHours(hours).plusMinutes(minutes);
	}

	/**
	 * Return if value of days or hours or minutes is defined,
	 * that is, if it has its default hour minutes and seconds values
	 * @return boolean value that indicate if days or hours or minutes is defined
	 */
	public boolean isNotDefinedValues() {
		return days == 0 && hours == 0 && minutes == 0;
	}

	@Override
	public String toString() {
		return "OpenVotingSessionTimeLimitRequest [days=" + days + ", hours=" + hours + ", minutes=" + minutes + "]";
	}

}
