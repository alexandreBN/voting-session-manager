package br.com.votingsessionmanager.commons.infrastructure.handler;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The class {@code ErrorCustom} is used to return errors
 */
public class ErrorCustom {
	private Set<FieldErrorCustom> errors;

	public ErrorCustom(Set<FieldErrorCustom> errors) {
		this.errors = errors;
	}

	public Set<FieldErrorCustom> getErrors() {
		return errors;
	}

	@JsonIgnore
	public Set<String> getErrorMessages() {
		return errors.stream().map(FieldErrorCustom::getErrorDescription).collect(Collectors.toSet());
	}

}
