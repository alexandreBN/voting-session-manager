package br.com.votingsessionmanager.votingsessionmanager.infrastructure.votingsession;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.votingsessionmanager.commons.application.exception.sessionvote.VotingSessionAlreadyReceiveVoteFromAssociate;
import br.com.votingsessionmanager.commons.application.exception.sessionvote.VotingSessionAreNotAbleToReceiveVotesOnAgenda;
import br.com.votingsessionmanager.commons.infrastructure.resource.URIConsumerResource;
import br.com.votingsessionmanager.votingsessionmanager.application.agenda.exception.InvalidAgendaResourceReferenceException;
import br.com.votingsessionmanager.votingsessionmanager.application.agenda.exception.InvalidAgendaResourceReferenceOnVotingSessionException;
import br.com.votingsessionmanager.votingsessionmanager.application.associate.exception.InvalidAssociateResourceReferenceException;
import br.com.votingsessionmanager.votingsessionmanager.application.votingsession.OpenVotingSessionService;
import br.com.votingsessionmanager.votingsessionmanager.application.votingsession.SaveAssociateVoteService;
import br.com.votingsessionmanager.votingsessionmanager.application.votingsession.ShowResultOfVotingSessionAgendaService;
import br.com.votingsessionmanager.votingsessionmanager.application.votingsession.request.VoteRequest;
import br.com.votingsessionmanager.votingsessionmanager.application.votingsession.request.VotingSessionRequest;
import br.com.votingsessionmanager.votingsessionmanager.domain.votingsession.VotingSession;
import br.com.votingsessionmanager.votingsessionmanager.domain.votingsession.VotingSessionResult;

@RestController
@RequestMapping("/voting-session")
public class VotingSessionController {

	@Autowired
	private OpenVotingSessionService openVotingSessionService;

	@Autowired
	private SaveAssociateVoteService saveAssociateVoteService;

	@Autowired
	private ShowResultOfVotingSessionAgendaService showResultOfVotingSessionAgendaService;
	
	@GetMapping("/agenda/{agendaId}/result")
	public VotingSessionResult result(@PathVariable Long agendaId) throws InvalidAgendaResourceReferenceOnVotingSessionException {
		VotingSessionResult result = showResultOfVotingSessionAgendaService.getResult(agendaId);
		return result;
	}

	@PostMapping
	public ResponseEntity<VotingSession> open(@RequestBody @Valid VotingSessionRequest request) throws InvalidAgendaResourceReferenceException {
		VotingSession votingSessionOpened = openVotingSessionService.open(request);
		URI uri = new URIConsumerResource(votingSessionOpened.getId()).getUri();
		return ResponseEntity.created(uri).body(votingSessionOpened);
	}

	@PostMapping("/vote")
	public Long vote(@RequestBody @Valid VoteRequest request) throws InvalidAgendaResourceReferenceException, InvalidAgendaResourceReferenceOnVotingSessionException, VotingSessionAreNotAbleToReceiveVotesOnAgenda, InvalidAssociateResourceReferenceException, VotingSessionAlreadyReceiveVoteFromAssociate {
		Long votingSessionIdThatReceivedVote = saveAssociateVoteService.save(request);
		return votingSessionIdThatReceivedVote;
	}

}
