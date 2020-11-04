package br.com.votingsessionmanager.commons.infrastructure.handler;

import java.util.Set;

public class ErrorCustom {
	private Set<FieldErrorCustom> errors;

	public ErrorCustom(Set<FieldErrorCustom> errors) {
		this.errors = errors;
	}

	public Set<FieldErrorCustom> getErrors() {
		return errors;
	}
}
