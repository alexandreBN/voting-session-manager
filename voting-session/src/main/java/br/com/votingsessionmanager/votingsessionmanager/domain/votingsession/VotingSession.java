package br.com.votingsessionmanager.votingsessionmanager.domain.votingsession;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.votingsessionmanager.votingsessionmanager.domain.agenda.Agenda;

@Entity
public class VotingSession {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "agenda_id")
	private Agenda agenda;

	@Column(name = "open_until")
	private LocalDateTime openUntil;

	private VotingSession() {
	}

	public VotingSession(Agenda agenda, LocalDateTime localDateTime) {
		this.agenda = agenda;
		this.openUntil = localDateTime;
	}

	public Long getId() {
		return id;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public LocalDateTime getOpenUntil() {
		return openUntil;
	}

}
