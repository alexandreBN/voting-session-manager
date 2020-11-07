package br.com.votingsessionmanager.votingsessionmanager.application.votingsession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.votingsessionmanager.votingsessionmanager.application.agenda.exception.InvalidAgendaResourceReferenceOnVotingSessionException;
import br.com.votingsessionmanager.votingsessionmanager.domain.votingsession.VotingSession;
import br.com.votingsessionmanager.votingsessionmanager.domain.votingsession.VotingSessionResult;
import br.com.votingsessionmanager.votingsessionmanager.infrastructure.votingsession.VotingSessionRepository;

@Service
public class ShowResultOfVotingSessionAgendaService {

	@Autowired
	private VotingSessionRepository votingSessionRepository;

	public VotingSessionResult getResult(Long agendaId) throws InvalidAgendaResourceReferenceOnVotingSessionException {
		VotingSession votingSession = votingSessionRepository.findByAgendaId(agendaId).orElseThrow(() -> new InvalidAgendaResourceReferenceOnVotingSessionException(agendaId));
		VotingSessionResult result = votingSession.getResult();
		return result;
	}

}
