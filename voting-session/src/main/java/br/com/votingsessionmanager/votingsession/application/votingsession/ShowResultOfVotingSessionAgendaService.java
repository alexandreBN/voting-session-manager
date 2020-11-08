package br.com.votingsessionmanager.votingsession.application.votingsession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.votingsessionmanager.votingsession.application.agenda.exception.InvalidAgendaResourceReferenceOnVotingSessionException;
import br.com.votingsessionmanager.votingsession.domain.votingsession.VotingSession;
import br.com.votingsessionmanager.votingsession.domain.votingsession.VotingSessionResult;
import br.com.votingsessionmanager.votingsession.infrastructure.votingsession.VotingSessionRepository;

@Service
public class ShowResultOfVotingSessionAgendaService {

	@Autowired
	private VotingSessionRepository votingSessionRepository;

	/**
	 * Show result from voting session agenda votes
	 * 
	 * @param agendaId agenda identifier
	 * @return voting session result
	 * @throws InvalidAgendaResourceReferenceOnVotingSessionException it will be throws if informed agenda is not part of relation of voting session
	 */
	public VotingSessionResult getResult(Long agendaId) throws InvalidAgendaResourceReferenceOnVotingSessionException {
		VotingSession votingSession = votingSessionRepository.findByAgendaId(agendaId).orElseThrow(() -> new InvalidAgendaResourceReferenceOnVotingSessionException(agendaId));
		VotingSessionResult result = votingSession.getResult();
		return result;
	}

}
