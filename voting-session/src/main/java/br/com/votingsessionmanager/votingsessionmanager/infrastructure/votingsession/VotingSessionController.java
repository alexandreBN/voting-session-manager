package br.com.votingsessionmanager.votingsessionmanager.infrastructure.votingsession;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.votingsessionmanager.commons.infrastructure.resource.URIConsumerResource;
import br.com.votingsessionmanager.votingsessionmanager.application.agenda.exception.InvalidAgendaResourceReferenceException;
import br.com.votingsessionmanager.votingsessionmanager.application.votingsession.OpenVotingSessionService;
import br.com.votingsessionmanager.votingsessionmanager.application.votingsession.request.VotingSessionRequest;
import br.com.votingsessionmanager.votingsessionmanager.domain.votingsession.VotingSession;

@RestController
@RequestMapping("/voting-session")
public class VotingSessionController {

	@Autowired
	private OpenVotingSessionService openVotingSessionService;
	
	@PostMapping
	public ResponseEntity<VotingSession> open(@RequestBody @Valid VotingSessionRequest request) throws InvalidAgendaResourceReferenceException {
		VotingSession votingSessionOpened = openVotingSessionService.open(request);
		URI uri = new URIConsumerResource(votingSessionOpened.getId()).getUri();
		return ResponseEntity.created(uri).body(votingSessionOpened);
	}

}
