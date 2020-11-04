package br.com.votingsessionmanager.votingsessionmanager.application.associate.request;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import br.com.votingsessionmanager.votingsessionmanager.domain.associate.Associate;

public class CreateAssociateRequest {

	@NotBlank
	@Length(max = 255)
	private String name;

	public String getName() {
		return name;
	}

	public Associate toEntity() {
		return new Associate(name);
	}

	@Override
	public String toString() {
		return "Associate [name=" + name + "]";
	}

}
