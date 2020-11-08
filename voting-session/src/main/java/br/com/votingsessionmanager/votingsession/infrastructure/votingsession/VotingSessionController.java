package br.com.votingsessionmanager.votingsession.infrastructure.votingsession;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.votingsessionmanager.commons.infrastructure.resource.URIConsumerResource;
import br.com.votingsessionmanager.votingsession.application.agenda.exception.InvalidAgendaResourceReferenceException;
import br.com.votingsessionmanager.votingsession.application.agenda.exception.InvalidAgendaResourceReferenceOnVotingSessionException;
import br.com.votingsessionmanager.votingsession.application.associate.SearchExternalAssociate;
import br.com.votingsessionmanager.votingsession.application.associate.SearchInternalDatabaseAssociate;
import br.com.votingsessionmanager.votingsession.application.votingsession.OpenVotingSessionService;
import br.com.votingsessionmanager.votingsession.application.votingsession.SaveAssociateVoteService;
import br.com.votingsessionmanager.votingsession.application.votingsession.ShowResultOfVotingSessionAgendaService;
import br.com.votingsessionmanager.votingsession.application.votingsession.request.OpenVotingSessionRequest;
import br.com.votingsessionmanager.votingsession.application.votingsession.request.OpenVotingSessionResponse;
import br.com.votingsessionmanager.votingsession.application.votingsession.request.VoteExternalAssociateRequest;
import br.com.votingsessionmanager.votingsession.application.votingsession.request.VoteRequest;
import br.com.votingsessionmanager.votingsession.domain.votingsession.VotingSession;
import br.com.votingsessionmanager.votingsession.domain.votingsession.VotingSessionResult;

@RestController
@RequestMapping("/voting-sessions")
public class VotingSessionController {

	@Autowired
	private OpenVotingSessionService openVotingSessionService;

	@Autowired
	private SaveAssociateVoteService saveAssociateVoteService;

	@Autowired
	private ShowResultOfVotingSessionAgendaService showResultOfVotingSessionAgendaService;
	
	@Autowired
	private SearchInternalDatabaseAssociate searchInternalDatabaseAssociate;

	@Autowired
	private SearchExternalAssociate searchExternalAssociate;

	private static final Logger logger = LoggerFactory.getLogger(VotingSessionController.class);

	@GetMapping("/agenda/{agendaId}/result")
	public VotingSessionResult result(@PathVariable Long agendaId) throws InvalidAgendaResourceReferenceOnVotingSessionException {
		logger.warn("Attempt to get voting session result from agenda with id {}", agendaId);
		VotingSessionResult result = showResultOfVotingSessionAgendaService.getResult(agendaId);
		logger.warn("Success on get voting session result from agenda with id {}", agendaId);
		return result;
	}

	@PostMapping
	public ResponseEntity<OpenVotingSessionResponse> open(@RequestBody @Valid OpenVotingSessionRequest request) throws InvalidAgendaResourceReferenceException {
		logger.warn("Attempt to open voting session from agenda with id {} and {}", request.getAgendaId(), request.getOpenUntil() != null ? "will be open to votes until based on data: " + request.getOpenUntil().toString() : "not receive open until data");
		VotingSession votingSessionOpened = openVotingSessionService.open(request);
		URI uri = new URIConsumerResource(votingSessionOpened.getId()).getUri();
		logger.warn("Success on open voting session from agenda with id {} and will be open until {}", request.getAgendaId(), votingSessionOpened.getOpenUntil());
		OpenVotingSessionResponse votingSessionOpenedResponse = new OpenVotingSessionResponse(votingSessionOpened.getId(), votingSessionOpened.getAgenda().getId(), votingSessionOpened.getOpenUntil());
		return ResponseEntity.created(uri).body(votingSessionOpenedResponse);
	}

	@PostMapping("/vote")
	public Long vote(@RequestBody @Valid VoteRequest request) throws Exception {
		logger.warn("Attempt to insert vote from associate with id {} on agenda with id {} ", request.getAssociateId(), request.getAgendaId());
		Long votingSessionIdThatReceivedVote = saveAssociateVoteService.save(request, searchInternalDatabaseAssociate);
		logger.warn("Success on insert vote from associate with id {} on agenda with id {} ", request.getAssociateId(), request.getAgendaId());
		return votingSessionIdThatReceivedVote;
	}

	@PostMapping("/vote/app")
	public Long voteByExternalApp(@RequestBody @Valid VoteExternalAssociateRequest request) throws Exception {
		logger.warn("Attempt to insert vote from external associate with id {} on agenda with id {} ", request.getAssociateId(), request.getAgendaId());
		Long votingSessionIdThatReceivedVote = saveAssociateVoteService.save(request, searchExternalAssociate);
		logger.warn("Success on insert vote from external associate with id {} on agenda with id {} ", request.getAssociateId(), request.getAgendaId());
		return votingSessionIdThatReceivedVote;
	}

}
