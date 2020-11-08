package br.com.votingsessionmanager.votingsession.infrastructure.agenda;

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

import br.com.votingsessionmanager.votingsession.domain.agenda.Agenda;
import br.com.votingsessionmanager.votingsession.environment.EnvironmentService;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AgendaControllerTest {

	private final String path = "/agendas";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EnvironmentService environmentService;

	@Test
	public void mustReturnStatusCodeCreatedAndSameRegisteredData() throws Exception {
		URI uri = new URI(path);

		CreateAgendaRequestBuilder builder = new CreateAgendaRequestBuilder();
		String agendaRequestAsString = builder
			.withName("Code Pattern")
			.withDescription("Patterns to be followed on development")
			.buildJSONString();

		ResultActions resultAction = mockMvc
				.perform(MockMvcRequestBuilders.post(uri).content(agendaRequestAsString).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));

		String responseBody = resultAction.andReturn().getResponse().getContentAsString();
		Agenda agendaSaved = (Agenda) builder.parse(responseBody, Agenda.class);

		assertEquals(agendaSaved.getName(), "Code Pattern");
		assertEquals(agendaSaved.getDescription(), "Patterns to be followed on development");
	}

	@Test
	public void mustReturnStatusCodeCreatedAndSameRegisteredDataAfterFindByAgendaId() throws Exception {
		URI uri = new URI(path);

		CreateAgendaRequestBuilder builder = new CreateAgendaRequestBuilder();
		String agendaRequestAsString = builder
			.withName("Code Pattern")
			.withDescription("Patterns to be followed on development")
			.buildJSONString();

		ResultActions resultAction = mockMvc.perform(MockMvcRequestBuilders
			.post(uri)
			.content(agendaRequestAsString)
			.contentType(MediaType.APPLICATION_JSON));
		
		String responseBodyOfCreateAgendaRequest = resultAction.andReturn().getResponse().getContentAsString();
		Agenda agendaSaved = (Agenda) builder.parse(responseBodyOfCreateAgendaRequest, Agenda.class);

		URI findAgendaUri = new URI(path.concat("/").concat(String.valueOf(agendaSaved.getId())));

		ResultActions findResultAction = mockMvc
				.perform(MockMvcRequestBuilders.get(findAgendaUri).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
	
		String responseBodyOfFindAgendaRequest = findResultAction.andReturn().getResponse().getContentAsString();
		Agenda agendaReturned = (Agenda) builder.parse(responseBodyOfFindAgendaRequest, Agenda.class);

		assertEquals(agendaReturned.getName(), "Code Pattern");
		assertEquals(agendaReturned.getDescription(), "Patterns to be followed on development");
	}

	@Test
	public void mustReturnStatusCodeNotFound() throws Exception {
		environmentService.deleteAll();
		URI findAgendaUri = new URI(path.concat("/").concat(String.valueOf(1)));

		mockMvc
			.perform(MockMvcRequestBuilders.get(findAgendaUri).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
	}

	@Test
	public void mustReturnStatusCodeBadRequestBecauseAgendaContainsInvalidEmptyName() throws Exception {
		URI uri = new URI(path);

		CreateAgendaRequestBuilder builder = new CreateAgendaRequestBuilder();
		String agendaRequestAsString = builder
			.withName("")
			.withDescription("Patterns to be followed on development")
			.buildJSONString();

		mockMvc
			.perform(MockMvcRequestBuilders.post(uri).content(agendaRequestAsString).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	public void mustReturnStatusCodeBadRequestBecauseAgendaContainsNullName() throws Exception {
		URI uri = new URI(path);

		CreateAgendaRequestBuilder builder = new CreateAgendaRequestBuilder();
		String agendaRequestAsString = builder
			.withName(null)
			.withDescription("Patterns to be followed on development")
			.buildJSONString();

		mockMvc
			.perform(MockMvcRequestBuilders.post(uri).content(agendaRequestAsString).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	public void mustReturnStatusCodeBadRequestBecauseAgendaContainALongerNameThanIsSupported() throws Exception {
		URI uri = new URI(path);

		CreateAgendaRequestBuilder builder = new CreateAgendaRequestBuilder();
		String agendaRequestAsString = builder
			.withName("Padrões de Desenvolvimento de Software Padrões de Desenvolvimento de Software Padrões de Desenvolvimento de SoftwarePadrões de Desenvolvimento de Software Padrões de Desenvolvimento de Software Padrões de Desenvolvimento de Software Padrões de Desenvolvime")
			.withDescription("Patterns to be followed on development")
			.buildJSONString();

		mockMvc
			.perform(MockMvcRequestBuilders.post(uri).content(agendaRequestAsString).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	public void mustReturnStatusCodeBadRequestBecauseAgendaContainALongerDescriptionThanIsSupported() throws Exception {
		URI uri = new URI(path);

		CreateAgendaRequestBuilder builder = new CreateAgendaRequestBuilder();
		String agendaRequestAsString = builder
			.withName("Padrões de Desenvolvimento de Software")
			.withDescription("O uso de um padrão de codificação também aumenta a produtividade num projeto, uma vez que a comunicação dentro da equipe de desenvolvimento fica mais fácil, mas vale ressaltar que partes desses padrões são vistas, algumas vezes, como sugestões por empresas")
			.buildJSONString();

		mockMvc
			.perform(MockMvcRequestBuilders.post(uri).content(agendaRequestAsString).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
	}

}
