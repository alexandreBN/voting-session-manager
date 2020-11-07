package br.com.votingsessionmanager.commons.infrastructure.handler;

import java.text.MessageFormat;

import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.CaseFormat;

public class FieldErrorCustom {
	private String field;

	@JsonProperty("error_description")
	protected String errorDescription;

	public FieldErrorCustom(FieldError fieldError) {
		this.field = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldError.getField());
		this.errorDescription = MessageFormat.format("{0} {1}", field, fieldError.getDefaultMessage());
	}

	public String getField() {
		return field;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

}
