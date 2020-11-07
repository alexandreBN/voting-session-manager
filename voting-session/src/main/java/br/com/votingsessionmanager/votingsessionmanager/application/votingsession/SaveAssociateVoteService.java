package br.com.votingsessionmanager.votingsessionmanager.application.votingsession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.votingsessionmanager.commons.application.exception.sessionvote.VotingSessionAlreadyReceiveVoteFromAssociate;
import br.com.votingsessionmanager.commons.application.exception.sessionvote.VotingSessionAreNotAbleToReceiveVotesOnAgenda;
import br.com.votingsessionmanager.votingsessionmanager.application.agenda.exception.InvalidAgendaResourceReferenceException;
import br.com.votingsessionmanager.votingsessionmanager.application.agenda.exception.InvalidAgendaResourceReferenceOnVotingSessionException;
import br.com.votingsessionmanager.votingsessionmanager.application.associate.exception.InvalidAssociateResourceReferenceException;
import br.com.votingsessionmanager.votingsessionmanager.application.votingsession.request.VoteRequest;
import br.com.votingsessionmanager.votingsessionmanager.domain.agenda.Vote;
import br.com.votingsessionmanager.votingsessionmanager.domain.associate.Associate;
import br.com.votingsessionmanager.votingsessionmanager.domain.votingsession.VotingSession;
import br.com.votingsessionmanager.votingsessionmanager.infrastructure.agenda.VoteRepository;
import br.com.votingsessionmanager.votingsessionmanager.infrastructure.associate.AssociateRepository;
import br.com.votingsessionmanager.votingsessionmanager.infrastructure.votingsession.VotingSessionRepository;

@Service
public class SaveAssociateVoteService {

	@Autowired
	private VoteRepository voteRepository;

	@Autowired
	private AssociateRepository associateRepository;

	@Autowired
	private VotingSessionRepository votingSessionRepository;

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
