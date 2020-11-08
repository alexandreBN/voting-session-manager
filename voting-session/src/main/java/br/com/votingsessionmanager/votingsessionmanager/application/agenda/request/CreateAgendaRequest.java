package br.com.votingsessionmanager.votingsessionmanager.application.agenda.request;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import br.com.votingsessionmanager.votingsessionmanager.domain.agenda.Agenda;

/**
 * The class {@code CreateAgendaRequest} is used populated when application
 * receive request to create a new agenda 
 */
public class CreateAgendaRequest {

	@NotBlank
	@Length(max = 255)
	private String name;

	@Length(max = 255)
	private String description;

	public CreateAgendaRequest(@NotBlank @Length(max = 255) String name, @Length(max = 255) String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	/**
	 * Parse {@code CreateAgendaRequest} to {@code Agenda}
	 * @return {@Agenda} with same of {@code CreateAgendaRequest} class
	 */
	public Agenda toEntity() {
		return new Agenda(name, description);
	}

	@Override
	public String toString() {
		return "Agenda [name=" + name + ", description=" + description + "]";
	}

}
