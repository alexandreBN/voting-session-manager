package br.com.votingsessionmanager.votingsession.application.associate;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.votingsessionmanager.votingsession.application.associate.exception.InvalidAssociateResourceReferenceException;
import br.com.votingsessionmanager.votingsession.domain.associate.Associate;
import br.com.votingsessionmanager.votingsession.infrastructure.associate.AssociateRepository;

/**
 * The abstract class {@code SearchInternalDatabaseAssociate} implement
 * a strategy to find associates based on identifier on internal database
 */
@Service
public class SearchInternalDatabaseAssociate extends FindAssociate {

	@Autowired
	private AssociateRepository repository;

	@Override
	public <T> Optional<Associate> find(T identifier) {
		Long associateId = Long.parseLong(String.valueOf(identifier));
		Optional<Associate> associateFound = repository.findById(associateId);
		return associateFound;
	}

	@Override
	public <T> void throwErrorIfAssociateIsNotAbleToVote(T identifier) throws InvalidAssociateResourceReferenceException, NumberFormatException {
		String associateId = String.valueOf(identifier);
		throw new InvalidAssociateResourceReferenceException(associateId);
	}

}
