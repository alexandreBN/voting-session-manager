package br.com.votingsessionmanager.votingsession.infrastructure.associate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.votingsessionmanager.votingsession.domain.associate.Associate;
import br.com.votingsessionmanager.votingsession.environment.EnvironmentService;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AssociateControllerTest {

	private final String path = "/associates";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EnvironmentService environmentService;

	@Test
	public void mustReturnStatusCodeCreatedAndSameRegisteredData() throws Exception {
		URI uri = new URI(path);

		CreateAssociateRequestBuilder builder = new CreateAssociateRequestBuilder();
		String associateRequestAsString = builder
			.withName("Alexandre")
			.buildJSONString();

		ResultActions resultAction = mockMvc
				.perform(MockMvcRequestBuilders.post(uri).content(associateRequestAsString).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));

		String responseBody = resultAction.andReturn().getResponse().getContentAsString();
		Associate associateSaved = (Associate) builder.parse(responseBody, Associate.class);

		assertEquals(associateSaved.getName(), "Alexandre");
	}

	@Test
	public void mustReturnStatusCodeCreatedAndSameRegisteredDataAfterFindByAssociateId() throws Exception {
		URI uri = new URI(path);

		CreateAssociateRequestBuilder builder = new CreateAssociateRequestBuilder();
		String associateRequestAsString = builder
			.withName("Alexandre")
			.buildJSONString();

		ResultActions resultAction = mockMvc
				.perform(MockMvcRequestBuilders.post(uri).content(associateRequestAsString).contentType(MediaType.APPLICATION_JSON));

		String responseBodyOfCreateAssociateRequest = resultAction.andReturn().getResponse().getContentAsString();
		Associate associateSaved = (Associate) builder.parse(responseBodyOfCreateAssociateRequest, Associate.class);

		URI findAssociateUri = new URI(path.concat("/").concat(String.valueOf(associateSaved .getId())));

		ResultActions findResultAction = mockMvc
				.perform(MockMvcRequestBuilders.get(findAssociateUri).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
	
		String responseBodyOfFindAssociateRequest = findResultAction.andReturn().getResponse().getContentAsString();
		Associate associateReturned = (Associate) builder.parse(responseBodyOfFindAssociateRequest, Associate.class);

		assertEquals(associateReturned.getName(), "Alexandre");
	}

	@Test
	public void mustReturnStatusCodeNotFound() throws Exception {
		environmentService.deleteAll();
		URI findAssociateUri = new URI(path.concat("/").concat(String.valueOf(1)));

		mockMvc
			.perform(MockMvcRequestBuilders.get(findAssociateUri).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
	}

	@Test
	public void mustReturnStatusCodeBadRequestBecauseAssociateContainsInvalidEmptyName() throws Exception {
		URI uri = new URI(path);

		String associateRequestAsString = new CreateAssociateRequestBuilder()
			.withName("")
			.buildJSONString();

		mockMvc
			.perform(MockMvcRequestBuilders.post(uri).content(associateRequestAsString).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	public void mustReturnStatusCodeBadRequestBecauseAssociateContainsNullName() throws Exception {
		URI uri = new URI(path);

		CreateAssociateRequestBuilder builder = new CreateAssociateRequestBuilder();
		String associateRequestAsString = builder
			.withName(null)
			.buildJSONString();

		mockMvc
			.perform(MockMvcRequestBuilders.post(uri).content(associateRequestAsString).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	public void mustReturnStatusCodeBadRequestBecauseAssociateContainALongerNameThanIsSupported() throws Exception {
		URI uri = new URI(path);

		String associateRequestAsString = new CreateAssociateRequestBuilder()
			.withName("ed Wacky League Antlez Broke the Stereo Neon Tide Bring Back Honesty Coalition Feedback Hand of Aces Keep Going Captain Let’s Pretend Lost State of Dance Paper Taxis Lunar Road Up Down Strange All and I Neon Sheep Eve Hornby Faye Bradley AJ Wilde Michael R")
			.buildJSONString();

		mockMvc
			.perform(MockMvcRequestBuilders.post(uri).content(associateRequestAsString).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
	}

}
