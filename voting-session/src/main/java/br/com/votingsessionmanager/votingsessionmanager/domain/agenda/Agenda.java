package br.com.votingsessionmanager.votingsessionmanager.domain.agenda;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Agenda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String description;

	@OneToMany
	private Set<Vote> votes;

	@SuppressWarnings("unused")
	private Agenda() {
	}

	public Agenda(String name, String description) {
		this.name = name;
		this.description = description;
		this.votes = new HashSet<>();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Set<Vote> getVotes() {
		return votes;
	}

}
