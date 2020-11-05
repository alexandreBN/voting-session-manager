package br.com.votingsessionmanager.votingsessionmanager.application.votingsession;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.votingsessionmanager.commons.application.exception.sessionvote.VotingSessionAlreadyOpenedWithAgendaException;
import br.com.votingsessionmanager.votingsessionmanager.application.agenda.exception.InvalidAgendaResourceReferenceException;
import br.com.votingsessionmanager.votingsessionmanager.application.votingsession.request.VotingSessionRequest;
import br.com.votingsessionmanager.votingsessionmanager.domain.agenda.Agenda;
import br.com.votingsessionmanager.votingsessionmanager.domain.votingsession.VotingSession;
import br.com.votingsessionmanager.votingsessionmanager.infrastructure.agenda.AgendaRepository;
import br.com.votingsessionmanager.votingsessionmanager.infrastructure.votingsession.VotingSessionRepository;

@Service
public class OpenVotingSessionService {

	@Autowired
	private VotingSessionRepository votingSessionRepository;

	@Autowired
	private AgendaRepository agendaRepository;

	public VotingSession open(@Valid VotingSessionRequest request) throws InvalidAgendaResourceReferenceException {
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
