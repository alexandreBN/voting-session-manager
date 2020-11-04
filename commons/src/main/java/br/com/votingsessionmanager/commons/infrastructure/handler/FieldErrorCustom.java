package br.com.votingsessionmanager.commons.infrastructure.handler;

import java.text.MessageFormat;

import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FieldErrorCustom {
	private String field;

	@JsonProperty("error_description")
	protected String errorDescription;

	public FieldErrorCustom(FieldError fieldError) {
		this.field = fieldError.getField();
		this.errorDescription = MessageFormat.format("{0} {1}", field, fieldError.getDefaultMessage());
	}

	public String getField() {
		return field;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

}
