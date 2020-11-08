package br.com.votingsessionmanager.votingsessionmanager.infrastructure.agenda;

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
import br.com.votingsessionmanager.votingsessionmanager.application.agenda.exception.InvalidAgendaResourceException;
import br.com.votingsessionmanager.votingsessionmanager.application.agenda.request.CreateAgendaRequest;
import br.com.votingsessionmanager.votingsessionmanager.domain.agenda.Agenda;

@RestController
@RequestMapping("/agendas")
public class AgendaController {

	@Autowired
	private AgendaRepository repository;

	private static final Logger logger = LoggerFactory.getLogger(AgendaController.class);

	@GetMapping
	public List<Agenda> findAll() {
		logger.warn("Attempt to get all agendas");
		List<Agenda> agendas = repository.findAll();
		logger.warn("Agendas has been returned with success");
		return agendas;
	}

	@GetMapping("/{id}")
	public Agenda findById(@PathVariable Long id) throws InvalidAgendaResourceException {
		logger.warn("Attempt to get agenda with id {} ", id);
		Agenda agenda = repository.findById(id).orElseThrow(() -> new InvalidAgendaResourceException(id));
		logger.warn("Agenda has been returned with sucess");
		return agenda;
	}

	@PostMapping
	public ResponseEntity<Agenda> save(@RequestBody @Valid CreateAgendaRequest request) {
		logger.warn("Attempt to save a new agenda with data {} ", request);
		Agenda agenda = repository.save(request.toEntity());
		URI uri = new URIConsumerResource(agenda.getId()).getUri();
		logger.warn("Agenda has been saved and can be consulted on {}", uri.toString());
		return ResponseEntity.created(uri).body(agenda);
	}

}
