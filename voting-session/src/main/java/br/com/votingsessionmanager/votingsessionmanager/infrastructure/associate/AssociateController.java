package br.com.votingsessionmanager.votingsessionmanager.infrastructure.associate;

import java.net.URI;
import java.util.List;

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
import br.com.votingsessionmanager.votingsessionmanager.application.associate.exception.InvalidAssociateResourceException;
import br.com.votingsessionmanager.votingsessionmanager.application.associate.request.CreateAssociateRequest;
import br.com.votingsessionmanager.votingsessionmanager.domain.associate.Associate;

@RestController
@RequestMapping("/associates")
public class AssociateController {

	@Autowired
	private AssociateRepository repository;

	private static final Logger logger = LoggerFactory.getLogger(AssociateController.class);

	@GetMapping
	public List<Associate> findAll() {
		logger.warn("Attempt to get all associates");
		List<Associate> associates = repository.findAll();
		logger.warn("Associates has been returned with success");
		return associates;
	}

	@GetMapping("/{id}")
	public Associate findById(@PathVariable Long id) throws InvalidAssociateResourceException {
		logger.warn("Attempt to get associate with id {} ", id);
		Associate associate = repository.findById(id).orElseThrow(() -> new InvalidAssociateResourceException(id));
		logger.warn("Associate has been returned with sucess");
		return associate;
	}

	@PostMapping
	public ResponseEntity<Associate> save(@RequestBody @Valid CreateAssociateRequest request) {
		logger.warn("Attempt to save a new associate with data {} ", request);
		Associate associate = repository.save(request.toEntity());
		URI uri = new URIConsumerResource(associate.getId()).getUri();
		logger.warn("Associate has been saved and can be consulted on {}", uri.toString());
		return ResponseEntity.created(uri).body(associate);
	}

}
