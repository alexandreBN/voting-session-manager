package br.com.votingsessionmanager.votingsessionmanager.infrastructure.votingsession;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.votingsessionmanager.votingsessionmanager.application.agenda.exception.InvalidAgendaResourceReferenceException;
import br.com.votingsessionmanager.votingsessionmanager.application.votingsession.OpenVotingSessionService;
import br.com.votingsessionmanager.votingsessionmanager.application.votingsession.request.VotingSessionRequest;
import br.com.votingsessionmanager.votingsessionmanager.domain.votingsession.VotingSession;
import br.com.votingsessionmanager.votingsessionmanager.infrastructure.agenda.AgendaRepository;

@RestController
@RequestMapping("/voting-session")
public class VotingSessionController {

	@Autowired
	private VotingSessionRepository repository;

	@Autowired
	private AgendaRepository agendaRepository;
	
	@Autowired
	private OpenVotingSessionService openVotingSessionService;
	
	@PostMapping
	public VotingSession open(@RequestBody @Valid VotingSessionRequest request) throws InvalidAgendaResourceReferenceException {
		VotingSession votingSessionOpened = openVotingSessionService.open(request);
		return votingSessionOpened;
	}

	@PostMapping("/vote")
	public VotingSession vote(@RequestBody @Valid VotingSessionRequest request) throws InvalidAgendaResourceReferenceException {
		VotingSession votingSessionOpened = openVotingSessionService.open(request);
		return votingSessionOpened;
	}
}
