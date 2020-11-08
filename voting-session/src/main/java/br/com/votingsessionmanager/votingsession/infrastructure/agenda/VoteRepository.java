package br.com.votingsessionmanager.votingsession.infrastructure.agenda;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.votingsessionmanager.votingsession.domain.agenda.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

}
