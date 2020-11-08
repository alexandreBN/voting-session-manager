package br.com.votingsessionmanager.votingsession.environment;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.votingsessionmanager.votingsession.infrastructure.agenda.AgendaRepository;
import br.com.votingsessionmanager.votingsession.infrastructure.agenda.VoteRepository;
import br.com.votingsessionmanager.votingsession.infrastructure.associate.AssociateRepository;
import br.com.votingsessionmanager.votingsession.infrastructure.votingsession.VotingSessionRepository;

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

	public AgendaRepository getAgendaRepository() {
		return agendaRepository;
	}

	public AssociateRepository getAssociateRepository() {
		return associateRepository;
	}

	public VotingSessionRepository getVotingSessionRepository() {
		return votingSessionRepository;
	}

	public String getRandomicString() {
        String characters = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder stringBuilder = new StringBuilder();

        Random random = new Random();
        while (stringBuilder.length() < 18) {
            int index = (int) (random.nextFloat() * characters.length());
            stringBuilder.append(characters.charAt(index));
        }

        String randomicString = stringBuilder.toString();
        return randomicString;
    }
}
