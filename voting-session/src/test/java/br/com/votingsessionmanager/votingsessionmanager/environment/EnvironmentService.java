package br.com.votingsessionmanager.votingsessionmanager.environment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.votingsessionmanager.votingsessionmanager.infrastructure.agenda.AgendaRepository;
import br.com.votingsessionmanager.votingsessionmanager.infrastructure.agenda.VoteRepository;
import br.com.votingsessionmanager.votingsessionmanager.infrastructure.associate.AssociateRepository;
import br.com.votingsessionmanager.votingsessionmanager.infrastructure.votingsession.VotingSessionRepository;

@Service
public class EnvironmentService {

	@Autowired
	private AgendaRepository agendaRepository;

	@Autowired
	private VoteRepository voteRepository;

	@Autowired
	private AssociateRepository associateRepository;

	@Autowired
	private VotingSessionRepository votingSessionRepository;

	public void deleteAll() {
		votingSessionRepository.deleteAll();
		voteRepository.deleteAll();
		agendaRepository.deleteAll();
		associateRepository.deleteAll();
	}
}
