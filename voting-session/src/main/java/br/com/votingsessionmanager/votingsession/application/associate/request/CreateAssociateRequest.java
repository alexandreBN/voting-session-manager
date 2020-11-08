package br.com.votingsessionmanager.votingsession.application.associate.request;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import br.com.votingsessionmanager.votingsession.domain.associate.Associate;

/**
 * The class {@code CreateAssociateRequest} is populated when application
 * receive request to create a new associate
 */
public class CreateAssociateRequest {

	@NotBlank
	@Length(max = 255)
	private String name;

	@SuppressWarnings("unused")
	private CreateAssociateRequest() {

	}

	public CreateAssociateRequest(@NotBlank @Length(max = 255) String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/**
	 * Parse {@code CreateAssociateRequest} to {@code Associate}
	 * @return {@Associate} with same of {@code CreateAssociateRequest} class
	 */
	public Associate toEntity() {
		return new Associate(name);
	}

	@Override
	public String toString() {
		return "Associate [name=" + name + "]";
	}

}
