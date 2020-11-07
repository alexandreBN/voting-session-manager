package br.com.votingsessionmanager.votingsessionmanager.infrastructure.votingsession;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.votingsessionmanager.votingsessionmanager.domain.votingsession.VotingSession;

public interface VotingSessionRepository extends JpaRepository<VotingSession, Long> {
	public Optional<VotingSession> findByAgendaId(Long agendaId);
}
