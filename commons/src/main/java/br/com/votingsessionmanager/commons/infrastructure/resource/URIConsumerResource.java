package br.com.votingsessionmanager.commons.infrastructure.resource;

import java.net.URI;

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
