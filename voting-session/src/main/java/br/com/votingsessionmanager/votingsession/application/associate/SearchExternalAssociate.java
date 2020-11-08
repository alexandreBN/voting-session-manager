package br.com.votingsessionmanager.votingsession.application.associate;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.votingsessionmanager.commons.application.exception.associate.AssociateUnableToVoteException;
import br.com.votingsessionmanager.votingsession.application.votingsession.request.FindExternalAssociateVoteStatusResponse;
import br.com.votingsessionmanager.votingsession.domain.associate.Associate;
import br.com.votingsessionmanager.votingsession.infrastructure.associate.AssociateRepository;

/**
 * The abstract class {@code SearchExternalAssociate} implement
 * a strategy to find associates based on identifier on external system
 */
@Service
public class SearchExternalAssociate extends FindAssociate {

	private final String url = "https://user-info.herokuapp.com/users/";

	@Autowired
	private AssociateRepository repository;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public <T> Optional<Associate> find(T identifier) {
		String associateId = String.valueOf(identifier);
		String urlWithUserIdentifier = url.concat(associateId);

		FindExternalAssociateVoteStatusResponse response = restTemplate.getForObject(urlWithUserIdentifier, FindExternalAssociateVoteStatusResponse.class);

		if(response.getStatus().isUnableToVote()) {
			return Optional.empty();
		}

		Optional<Associate> associateFound = repository.findByCpf(associateId);

		if(!associateFound.isPresent()) {
			Associate associate = new Associate("Anonymous", associateId);
			Associate associateSaved = repository.save(associate);
			associateFound = Optional.of(associateSaved);
		}

		return associateFound;
	}

	@Override
	public <T> void throwErrorIfAssociateIsNotAbleToVote(T identifier) throws AssociateUnableToVoteException {
		String associateId = String.valueOf(identifier);
		throw new AssociateUnableToVoteException(associateId);
	}

}
