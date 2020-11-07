package br.com.votingsessionmanager.votingsessionmanager.application.votingsession.request;

import java.time.LocalDateTime;

import javax.validation.constraints.PositiveOrZero;

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

	public LocalDateTime duration() {
		return LocalDateTime.now().plusDays(days).plusHours(hours).plusMinutes(minutes);
	}
	
	public boolean isNotDefinedValues() {
		return days == 0 && hours == 0 && minutes == 0;
	}

	@Override
	public String toString() {
		return "OpenVotingSessionTimeLimitRequest [days=" + days + ", hours=" + hours + ", minutes=" + minutes + "]";
	}

}
