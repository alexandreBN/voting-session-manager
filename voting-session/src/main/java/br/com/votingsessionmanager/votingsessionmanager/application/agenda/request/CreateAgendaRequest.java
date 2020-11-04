package br.com.votingsessionmanager.votingsessionmanager.application.agenda.request;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import br.com.votingsessionmanager.votingsessionmanager.domain.agenda.Agenda;

public class CreateAgendaRequest {

	@NotBlank
	@Length(max = 255)
	private String name;

	@NotBlank
	@Length(max = 255)
	private String description;

	public CreateAgendaRequest(@NotBlank @Length(max = 255) String name,
			@NotBlank @Length(max = 255) String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Agenda toEntity() {
		return new Agenda(name, description);
	}

	@Override
	public String toString() {
		return "Agenda [name=" + name + ", description=" + description + "]";
	}

}
