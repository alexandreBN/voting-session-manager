package br.com.votingsessionmanager.votingsessionmanager.infrastructure.agenda;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.votingsessionmanager.votingsessionmanager.domain.agenda.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

}
