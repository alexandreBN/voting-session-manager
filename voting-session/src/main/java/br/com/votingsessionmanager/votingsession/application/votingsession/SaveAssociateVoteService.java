package br.com.votingsessionmanager.votingsession.application.votingsession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.votingsessionmanager.commons.application.exception.sessionvote.VotingSessionAlreadyReceiveVoteFromAssociate;
import br.com.votingsessionmanager.commons.application.exception.sessionvote.VotingSessionAreNotAbleToReceiveVotesOnAgenda;
import br.com.votingsessionmanager.votingsession.application.agenda.exception.InvalidAgendaResourceReferenceException;
import br.com.votingsessionmanager.votingsession.application.agenda.exception.InvalidAgendaResourceReferenceOnVotingSessionException;
import br.com.votingsessionmanager.votingsession.application.associate.exception.InvalidAssociateResourceReferenceException;
import br.com.votingsessionmanager.votingsession.application.votingsession.request.VoteRequest;
import br.com.votingsessionmanager.votingsession.domain.agenda.Vote;
import br.com.votingsessionmanager.votingsession.domain.associate.Associate;
import br.com.votingsessionmanager.votingsession.domain.votingsession.VotingSession;
import br.com.votingsessionmanager.votingsession.infrastructure.agenda.VoteRepository;
import br.com.votingsessionmanager.votingsession.infrastructure.associate.AssociateRepository;
import br.com.votingsessionmanager.votingsession.infrastructure.votingsession.VotingSessionRepository;

@Service
public class SaveAssociateVoteService {

	@Autowired
	private VoteRepository voteRepository;

	@Autowired
	private AssociateRepository associateRepository;

	@Autowired
	private VotingSessionRepository votingSessionRepository;
	
	/**
	 * Save associate vote on voting session, identified by agenda
	 * 
	 * @param request vote data
	 * @return voting session identifier
	 * @throws InvalidAgendaResourceReferenceException it will be throws if agenda reference is invalid
	 * @throws InvalidAgendaResourceReferenceOnVotingSessionException it will be throws if informed agenda is not part of relation of voting session
	 * @throws VotingSessionAreNotAbleToReceiveVotesOnAgenda it will be throws if open_until time is past from current date
	 * @throws InvalidAssociateResourceReferenceException it will be throws if associate reference is invalid
	 * @throws VotingSessionAlreadyReceiveVoteFromAssociate it will be throws if voting session already receive vote from informed associate
	 */
	public Long save(VoteRequest request) throws InvalidAgendaResourceReferenceException, InvalidAgendaResourceReferenceOnVotingSessionException, VotingSessionAreNotAbleToReceiveVotesOnAgenda, InvalidAssociateResourceReferenceException, VotingSessionAlreadyReceiveVoteFromAssociate {

		VotingSession votingSession = votingSessionRepository.findByAgendaId(request.getAgendaId()).orElseThrow(() -> new InvalidAgendaResourceReferenceOnVotingSessionException(request.getAgendaId()));
		boolean votingSessionCanReceiveVotes = votingSession.canReceiveVotes();

		if(!votingSessionCanReceiveVotes) {
			throw new VotingSessionAreNotAbleToReceiveVotesOnAgenda(request.getAgendaId());
		}

		Associate associate = associateRepository.findById(request.getAssociateId()).orElseThrow(() -> new InvalidAssociateResourceReferenceException(request.getAssociateId()));
		boolean alreadyReceiveVotesAssociate = votingSession.alreadyReceiveVotesAssociate(associate.getId());

		if(alreadyReceiveVotesAssociate) {
			throw new VotingSessionAlreadyReceiveVoteFromAssociate(associate.getId());
		}

		Vote vote = new Vote(associate, request.getVote());
		voteRepository.save(vote);

		votingSession.add(vote);

		VotingSession votingSessionSaved = votingSessionRepository.save(votingSession);
		return votingSessionSaved.getId();
	}

}
