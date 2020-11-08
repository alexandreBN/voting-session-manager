package br.com.votingsessionmanager.votingsession.application.votingsession;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import br.com.votingsessionmanager.commons.application.exception.sessionvote.VotingSessionAlreadyReceiveVoteFromAssociate;
import br.com.votingsessionmanager.commons.application.exception.sessionvote.VotingSessionAreNotAbleToReceiveVotesOnAgenda;
import br.com.votingsessionmanager.votingsession.application.agenda.exception.InvalidAgendaResourceReferenceOnVotingSessionException;
import br.com.votingsessionmanager.votingsession.application.associate.FindAssociate;
import br.com.votingsessionmanager.votingsession.application.associate.exception.InvalidAssociateResourceException;
import br.com.votingsessionmanager.votingsession.application.associate.exception.InvalidAssociateResourceReferenceException;
import br.com.votingsessionmanager.votingsession.application.votingsession.request.GenericVoteRequest;
import br.com.votingsessionmanager.votingsession.domain.agenda.Vote;
import br.com.votingsessionmanager.votingsession.domain.associate.Associate;
import br.com.votingsessionmanager.votingsession.domain.votingsession.VotingSession;
import br.com.votingsessionmanager.votingsession.infrastructure.agenda.VoteRepository;
import br.com.votingsessionmanager.votingsession.infrastructure.votingsession.VotingSessionRepository;

@Service
public class SaveAssociateVoteService {

	@Autowired
	private VoteRepository voteRepository;

	@Autowired
	private VotingSessionRepository votingSessionRepository;

	/**
	 * Save associate vote on voting session, identified by agenda
	 * 
	 * @param request vote data
	 * @param searchAssociate strategy to search associate
	 * @return voting session identifier
	 * @throws Exception
	 * 	InvalidAgendaResourceReferenceException it will be throws if agenda reference is invalid
	 * 	InvalidAgendaResourceReferenceOnVotingSessionException it will be throws if informed agenda is not part of relation of voting session
	 * 	VotingSessionAreNotAbleToReceiveVotesOnAgenda it will be throws if open_until time is past from current date
	 *  InvalidAssociateResourceReferenceException it will be throws if associate reference is invalid
	 * 	VotingSessionAlreadyReceiveVoteFromAssociate it will be throws if voting session already receive vote from informed associate 
	 * 	AssociateUnableToVoteException it will be throws if external associate is unable to vote
	 */
	public Long save(GenericVoteRequest request, FindAssociate searchAssociate) throws Exception {

		VotingSession votingSession = votingSessionRepository.findByAgendaId(request.getAgendaId()).orElseThrow(() -> new InvalidAgendaResourceReferenceOnVotingSessionException(request.getAgendaId()));
		boolean votingSessionCanReceiveVotes = votingSession.canReceiveVotes();

		if(!votingSessionCanReceiveVotes) {
			throw new VotingSessionAreNotAbleToReceiveVotesOnAgenda(request.getAgendaId());
		}

		try {
			Optional<Associate> associateFound = searchAssociate.find(request.getAssociateId());
			
			if(!associateFound.isPresent()) {
				searchAssociate.throwErrorIfAssociateIsNotAbleToVote(request.getAssociateId());
			}

			Associate associate = associateFound.get();

			boolean alreadyReceiveVotesAssociate = votingSession.alreadyReceiveVotesAssociate(associate.getId());

			if(alreadyReceiveVotesAssociate) {
				throw new VotingSessionAlreadyReceiveVoteFromAssociate(associate.getId());
			}

			Vote vote = new Vote(associate, request.getVote());
			voteRepository.save(vote);

			votingSession.add(vote);

			VotingSession votingSessionSaved = votingSessionRepository.save(votingSession);
			return votingSessionSaved.getId();
		} catch(HttpClientErrorException ex) {

			if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new InvalidAssociateResourceReferenceException(request.getAssociateId());
			}

			throw ex;
		}

	}

}
