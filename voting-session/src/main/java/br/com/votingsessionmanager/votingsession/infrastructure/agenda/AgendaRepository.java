package br.com.votingsessionmanager.votingsession.infrastructure.agenda;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.votingsessionmanager.votingsession.domain.agenda.Agenda;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {

}
