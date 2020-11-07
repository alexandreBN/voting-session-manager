package br.com.votingsessionmanager.votingsessionmanager.infrastructure.agenda;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.votingsessionmanager.votingsessionmanager.domain.agenda.Agenda;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {

}
