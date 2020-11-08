package br.com.votingsessionmanager.votingsession.application.votingsession;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.votingsessionmanager.commons.application.exception.sessionvote.VotingSessionAlreadyOpenedWithAgendaException;
import br.com.votingsessionmanager.votingsession.application.agenda.exception.InvalidAgendaResourceReferenceException;
import br.com.votingsessionmanager.votingsession.application.votingsession.request.OpenVotingSessionRequest;
import br.com.votingsessionmanager.votingsession.domain.agenda.Agenda;
import br.com.votingsessionmanager.votingsession.domain.votingsession.VotingSession;
import br.com.votingsessionmanager.votingsession.infrastructure.agenda.AgendaRepository;
import br.com.votingsessionmanager.votingsession.infrastructure.votingsession.VotingSessionRepository;

@Service
public class OpenVotingSessionService {

	@Autowired
	private VotingSessionRepository votingSessionRepository;

	@Autowired
	private AgendaRepository agendaRepository;

	/**
	 * Open a voting session based on agenda
	 * 
	 * @param request data of voting session that are been opened
	 * @return voting session data that are been opened
	 * @throws InvalidAgendaResourceReferenceException it will be throws if agenda reference is invalid
	 */
	public VotingSession open(@Valid OpenVotingSessionRequest request) throws InvalidAgendaResourceReferenceException {
		Agenda agenda = agendaRepository.findById(request.getAgendaId()).orElseThrow(() -> new InvalidAgendaResourceReferenceException(request.getAgendaId()));
		Optional<VotingSession> votingSessionWithSpecificAgenda = votingSessionRepository.findByAgendaId(request.getAgendaId());

		votingSessionWithSpecificAgenda.ifPresent(c -> {
			throw new VotingSessionAlreadyOpenedWithAgendaException(agenda.getId());
		});

		VotingSession votingSession = new VotingSession(agenda, request.duration());
		votingSessionRepository.save(votingSession);
		return votingSession;
	}

}