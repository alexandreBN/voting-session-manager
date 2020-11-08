package br.com.votingsessionmanager.commons.infrastructure.resource;

import java.net.URI;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * The class {@code URIResource} define methods to be implemented on consumer resource
 */
public interface URIResource {
	URI getUri();

	default ServletUriComponentsBuilder getCurrentRequestUri() {
		ServletUriComponentsBuilder currentRequestUri = ServletUriComponentsBuilder.fromCurrentRequestUri();
		return currentRequestUri;
	}

}
