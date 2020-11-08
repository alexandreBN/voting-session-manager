package br.com.votingsessionmanager.votingsession.infrastructure.votingsession;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.votingsessionmanager.votingsession.domain.votingsession.VotingSession;

public interface VotingSessionRepository extends JpaRepository<VotingSession, Long> {
	public Optional<VotingSession> findByAgendaId(Long agendaId);
}
