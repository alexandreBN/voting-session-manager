package br.com.votingsessionmanager.commons.infrastructure.resource;

import java.net.URI;

/**
 * The class {@code URIConsumerResource} is used to 
 * build a resource URI data 
 */
public class URIConsumerResource implements URIResource {

	private Long id;

	public URIConsumerResource(Long id) {
		this.id = id;
	}

	@Override
	public URI getUri() {
		URI uri = getCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
		return uri;
	}

}
