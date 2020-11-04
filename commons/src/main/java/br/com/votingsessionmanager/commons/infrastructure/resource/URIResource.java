package br.com.votingsessionmanager.commons.infrastructure.resource;

import java.net.URI;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public interface URIResource {
	URI getUri();
	
	default ServletUriComponentsBuilder getCurrentRequestUri() {
		ServletUriComponentsBuilder currentRequestUri = ServletUriComponentsBuilder.fromCurrentRequestUri();
		return currentRequestUri;
	}

}
