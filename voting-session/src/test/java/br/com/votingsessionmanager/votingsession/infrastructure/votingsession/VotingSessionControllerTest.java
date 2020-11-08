package br.com.votingsessionmanager.votingsession.infrastructure.votingsession;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;

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

import br.com.votingsessionmanager.votingsession.application.votingsession.request.OpenVotingSessionResponse;
import br.com.votingsessionmanager.votingsession.domain.agenda.Agenda;
import br.com.votingsessionmanager.votingsession.domain.agenda.VoteType;
import br.com.votingsessionmanager.votingsession.domain.associate.Associate;
import br.com.votingsessionmanager.votingsession.domain.votingsession.VotingSession;
import br.com.votingsessionmanager.votingsession.domain.votingsession.VotingSessionResult;
import br.com.votingsessionmanager.votingsession.environment.EnvironmentService;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class VotingSessionControllerTest {

	private final String path = "/voting-sessions";
	private final String votePath = path.concat("/vote");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EnvironmentService environmentService;
	
	@Test
	public void mustReturnStatusCodeCreatedAndVotingSessionOpenedWithoutHours() throws Exception {
		URI uri = new URI(path);

		Agenda agenda = new Agenda("Padrões de Desenvolvimento de Software", "O uso de um padrão de codificação também aumenta a produtividade num projeto, uma vez que a comunicação dentro da equipe de desenvolvimento fica mais fácil.");
		Agenda agendaSaved = environmentService.getAgendaRepository().save(agenda);

		OpenVotingSessionRequestBuilder builder = new OpenVotingSessionRequestBuilder();
		String associateRequestAsString = builder
				.withAgendaId(agendaSaved.getId())
				.buildJSONString();

		ResultActions resultAction = mockMvc
				.perform(MockMvcRequestBuilders.post(uri).content(associateRequestAsString).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));

		String responseBody = resultAction.andReturn().getResponse().getContentAsString();
		OpenVotingSessionResponse votingSessionOpened = (OpenVotingSessionResponse) builder.parse(responseBody, OpenVotingSessionResponse.class);

		LocalDateTime now = LocalDateTime.now().plusMinutes(1);
		LocalDateTime openUntil = votingSessionOpened.getOpenUntil();

		assertEquals(votingSessionOpened.getAgenda().getId(), agendaSaved.getId());
		assertEquals(now.getDayOfMonth(), openUntil.getDayOfMonth());
		assertEquals(now.getHour(), openUntil.getHour());
		assertEquals(now.getMinute(), openUntil.getMinute());
	}
	
	@Test
	public void mustReturnStatusCodeCreatedAndVotingSessionOpenedWithSpecifiedHours() throws Exception {
		URI uri = new URI(path);

		Agenda agenda = new Agenda("Padrões de Desenvolvimento de Software", "O uso de um padrão de codificação também aumenta a produtividade num projeto, uma vez que a comunicação dentro da equipe de desenvolvimento fica mais fácil.");
		Agenda agendaSaved = environmentService.getAgendaRepository().save(agenda);

		OpenVotingSessionRequestBuilder builder = new OpenVotingSessionRequestBuilder();
		String associateRequestAsString = builder
			.withAgendaId(agendaSaved.getId())
			.withOpenedToVotesOnMaxDays(10)
			.withOpenedToVotesOnMaxHours(5)
			.withOpenedToVotesOnMaxMinutes(10)
			.buildJSONString();

		ResultActions resultAction = mockMvc
			.perform(MockMvcRequestBuilders.post(uri).content(associateRequestAsString).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));

		String responseBody = resultAction.andReturn().getResponse().getContentAsString();
		OpenVotingSessionResponse votingSessionOpened = (OpenVotingSessionResponse) builder.parse(responseBody, OpenVotingSessionResponse.class);

		LocalDateTime now = LocalDateTime.now().plusDays(10).plusHours(5).plusMinutes(10);
		LocalDateTime openUntil = votingSessionOpened.getOpenUntil();

		assertEquals(votingSessionOpened.getAgenda().getId(), agendaSaved.getId());
		assertEquals(now.getDayOfMonth(), openUntil.getDayOfMonth());
		assertEquals(now.getHour(), openUntil.getHour());
		assertEquals(now.getMinute(), openUntil.getMinute());
	}

	@Test
	public void mustReturnStatusCodeBadRequestBecauseTimeLimitVotingSessionContainsInvalidDays() throws Exception {
		URI uri = new URI(path);

		Agenda agenda = new Agenda("Padrões de Desenvolvimento de Software", "O uso de um padrão de codificação também aumenta a produtividade num projeto, uma vez que a comunicação dentro da equipe de desenvolvimento fica mais fácil.");
		Agenda agendaSaved = environmentService.getAgendaRepository().save(agenda);

		OpenVotingSessionRequestBuilder builder = new OpenVotingSessionRequestBuilder();
		String associateRequestAsString = builder
			.withAgendaId(agendaSaved.getId())
			.withOpenedToVotesOnMaxDays(-1)
			.withOpenedToVotesOnMaxHours(5)
			.withOpenedToVotesOnMaxMinutes(10)
			.buildJSONString();

		mockMvc
			.perform(MockMvcRequestBuilders.post(uri).content(associateRequestAsString).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	public void mustReturnStatusCodeBadRequestBecauseTimeLimitVotingSessionContainsInvalidHours() throws Exception {
		URI uri = new URI(path);

		Agenda agenda = new Agenda("Padrões de Desenvolvimento de Software", "O uso de um padrão de codificação também aumenta a produtividade num projeto, uma vez que a comunicação dentro da equipe de desenvolvimento fica mais fácil.");
		Agenda agendaSaved = environmentService.getAgendaRepository().save(agenda);

		OpenVotingSessionRequestBuilder builder = new OpenVotingSessionRequestBuilder();
		String associateRequestAsString = builder
			.withAgendaId(agendaSaved.getId())
			.withOpenedToVotesOnMaxDays(10)
			.withOpenedToVotesOnMaxHours(-1)
			.withOpenedToVotesOnMaxMinutes(10)
			.buildJSONString();

		mockMvc
			.perform(MockMvcRequestBuilders.post(uri).content(associateRequestAsString).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	public void mustReturnStatusCodeBadRequestBecauseTimeLimitVotingSessionContainsInvalidMinutes() throws Exception {
		URI uri = new URI(path);

		Agenda agenda = new Agenda("Padrões de Desenvolvimento de Software", "O uso de um padrão de codificação também aumenta a produtividade num projeto, uma vez que a comunicação dentro da equipe de desenvolvimento fica mais fácil.");
		Agenda agendaSaved = environmentService.getAgendaRepository().save(agenda);

		OpenVotingSessionRequestBuilder builder = new OpenVotingSessionRequestBuilder();
		String associateRequestAsString = builder
			.withAgendaId(agendaSaved.getId())
			.withOpenedToVotesOnMaxDays(10)
			.withOpenedToVotesOnMaxHours(5)
			.withOpenedToVotesOnMaxMinutes(-1)
			.buildJSONString();

		mockMvc
			.perform(MockMvcRequestBuilders.post(uri).content(associateRequestAsString).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	public void mustReturnStatusCodeBadRequestBecauseVotingSessionAlreadyWithAgendaIdIsAlreadyOpened() throws Exception {
		URI uri = new URI(path);

		Agenda agenda = new Agenda("Padrões de Desenvolvimento de Software", "O uso de um padrão de codificação também aumenta a produtividade num projeto, uma vez que a comunicação dentro da equipe de desenvolvimento fica mais fácil.");
		Agenda agendaSaved = environmentService.getAgendaRepository().save(agenda);

		OpenVotingSessionRequestBuilder builder = new OpenVotingSessionRequestBuilder();
		String associateRequestAsString = builder
			.withAgendaId(agendaSaved.getId())
			.withOpenedToVotesOnMaxDays(10)
			.withOpenedToVotesOnMaxHours(5)
			.withOpenedToVotesOnMaxMinutes(10)
			.buildJSONString();

		mockMvc
			.perform(MockMvcRequestBuilders.post(uri).content(associateRequestAsString).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));

		mockMvc
			.perform(MockMvcRequestBuilders.post(uri).content(associateRequestAsString).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));

	}

	@Test
	public void mustReturnStatusCodeBadRequestBecauseAgendaIdIsInvalid() throws Exception {
		environmentService.deleteAll();

		URI uri = new URI(path);

		OpenVotingSessionRequestBuilder builder = new OpenVotingSessionRequestBuilder();
		String associateRequestAsString = builder
			.withAgendaId(new Long(1))
			.withOpenedToVotesOnMaxDays(10)
			.withOpenedToVotesOnMaxHours(5)
			.withOpenedToVotesOnMaxMinutes(10)
			.buildJSONString();

		mockMvc
			.perform(MockMvcRequestBuilders.post(uri).content(associateRequestAsString).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	public void mustReturnStatusCodeOkAndRegisteredVoteYes() throws Exception {
		URI uri = new URI(path);

		Associate associate = new Associate("Alexandre");
		Associate associateSaved = environmentService.getAssociateRepository().save(associate);

		Agenda agenda = new Agenda("Padrões de Desenvolvimento de Software", "O uso de um padrão de codificação também aumenta a produtividade num projeto, uma vez que a comunicação dentro da equipe de desenvolvimento fica mais fácil.");
		Agenda agendaSaved = environmentService.getAgendaRepository().save(agenda);

		OpenVotingSessionRequestBuilder builder = new OpenVotingSessionRequestBuilder();
		String associateRequestAsString = builder
			.withAgendaId(agendaSaved.getId())
			.withOpenedToVotesOnMaxDays(1)
			.withOpenedToVotesOnMaxHours(0)
			.withOpenedToVotesOnMaxMinutes(0)
			.buildJSONString();

		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(associateRequestAsString).contentType(MediaType.APPLICATION_JSON));

		String voteRequestAsString = new CreateVoteRequestBuilder()
			.withAssociateId(associateSaved.getId())
			.withAgendaId(agendaSaved.getId())
			.withVote(VoteType.YES)
			.buildJSONString();

		URI voteUri = new URI(votePath);
		mockMvc
			.perform(MockMvcRequestBuilders.put(voteUri).content(voteRequestAsString).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
	}

	@Test
	public void mustReturnStatusCodeOkAndRegisteredVoteNo() throws Exception {
		URI uri = new URI(path);

		Associate associate = new Associate("Alexandre");
		Associate associateSaved = environmentService.getAssociateRepository().save(associate);

		Agenda agenda = new Agenda("Padrões de Desenvolvimento de Software", "O uso de um padrão de codificação também aumenta a produtividade num projeto, uma vez que a comunicação dentro da equipe de desenvolvimento fica mais fácil.");
		Agenda agendaSaved = environmentService.getAgendaRepository().save(agenda);

		OpenVotingSessionRequestBuilder builder = new OpenVotingSessionRequestBuilder();
		String associateRequestAsString = builder
			.withAgendaId(agendaSaved.getId())
			.withOpenedToVotesOnMaxDays(1)
			.withOpenedToVotesOnMaxHours(0)
			.withOpenedToVotesOnMaxMinutes(0)
			.buildJSONString();

		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(associateRequestAsString).contentType(MediaType.APPLICATION_JSON));

		String voteRequestAsString = new CreateVoteRequestBuilder()
			.withAssociateId(associateSaved.getId())
			.withAgendaId(agendaSaved.getId())
			.withVote(VoteType.NO)
			.buildJSONString();

		URI voteUri = new URI(votePath);
		mockMvc
			.perform(MockMvcRequestBuilders.put(voteUri).content(voteRequestAsString).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
	}

	@Test
	public void mustReturnStatusCodeBadRequestBecauseInformedAgendaIdOnVotingIsInvalid() throws Exception {
		URI uri = new URI(path);

		Associate associate = new Associate("Alexandre");
		Associate associateSaved = environmentService.getAssociateRepository().save(associate);

		Agenda agenda = new Agenda("Padrões de Desenvolvimento de Software", "O uso de um padrão de codificação também aumenta a produtividade num projeto, uma vez que a comunicação dentro da equipe de desenvolvimento fica mais fácil.");
		Agenda agendaSaved = environmentService.getAgendaRepository().save(agenda);

		OpenVotingSessionRequestBuilder builder = new OpenVotingSessionRequestBuilder();
		String associateRequestAsString = builder
			.withAgendaId(agendaSaved.getId())
			.withOpenedToVotesOnMaxDays(1)
			.withOpenedToVotesOnMaxHours(0)
			.withOpenedToVotesOnMaxMinutes(0)
			.buildJSONString();

		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(associateRequestAsString).contentType(MediaType.APPLICATION_JSON));

		String voteRequestAsString = new CreateVoteRequestBuilder()
			.withAssociateId(associateSaved.getId())
			.withAgendaId(new Long(0))
			.withVote(VoteType.NO)
			.buildJSONString();

		URI voteUri = new URI(votePath);
		mockMvc
			.perform(MockMvcRequestBuilders.put(voteUri).content(voteRequestAsString).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	public void mustReturnStatusCodeBadRequestBecauseInformedAssociateIdOnVotingIsInvalid() throws Exception {
		URI uri = new URI(path);

		Associate associate = new Associate("Alexandre");
		environmentService.getAssociateRepository().save(associate);

		Agenda agenda = new Agenda("Padrões de Desenvolvimento de Software", "O uso de um padrão de codificação também aumenta a produtividade num projeto, uma vez que a comunicação dentro da equipe de desenvolvimento fica mais fácil.");
		Agenda agendaSaved = environmentService.getAgendaRepository().save(agenda);

		OpenVotingSessionRequestBuilder builder = new OpenVotingSessionRequestBuilder();
		String associateRequestAsString = builder
			.withAgendaId(agendaSaved.getId())
			.withOpenedToVotesOnMaxDays(1)
			.withOpenedToVotesOnMaxHours(0)
			.withOpenedToVotesOnMaxMinutes(0)
			.buildJSONString();

		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(associateRequestAsString).contentType(MediaType.APPLICATION_JSON));

		String voteRequestAsString = new CreateVoteRequestBuilder()
			.withAssociateId(new Long(0))
			.withAgendaId(agendaSaved.getId())
			.withVote(VoteType.NO)
			.buildJSONString();

		URI voteUri = new URI(votePath);
		mockMvc
			.perform(MockMvcRequestBuilders.put(voteUri).content(voteRequestAsString).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	public void mustReturnStatusCodeBadRequestBecauseInformedVoteIsInvalid() throws Exception {
		URI uri = new URI(path);

		Associate associate = new Associate("Alexandre");
		Associate associateSaved = environmentService.getAssociateRepository().save(associate);

		Agenda agenda = new Agenda("Padrões de Desenvolvimento de Software", "O uso de um padrão de codificação também aumenta a produtividade num projeto, uma vez que a comunicação dentro da equipe de desenvolvimento fica mais fácil.");
		Agenda agendaSaved = environmentService.getAgendaRepository().save(agenda);

		OpenVotingSessionRequestBuilder builder = new OpenVotingSessionRequestBuilder();
		String associateRequestAsString = builder
			.withAgendaId(agendaSaved.getId())
			.withOpenedToVotesOnMaxDays(1)
			.withOpenedToVotesOnMaxHours(0)
			.withOpenedToVotesOnMaxMinutes(0)
			.buildJSONString();

		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(associateRequestAsString).contentType(MediaType.APPLICATION_JSON));

		String voteRequestAsString = new CreateVoteRequestBuilder()
			.withAssociateId(associateSaved.getId())
			.withAgendaId(agendaSaved.getId())
			.withVote(null)
			.buildJSONString();

		URI voteUri = new URI(votePath);
		mockMvc
			.perform(MockMvcRequestBuilders.put(voteUri).content(voteRequestAsString).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	public void mustReturnStatusCodeBadRequestBecauseVotingSessionIsAlreadyClosed() throws Exception {
		Associate associate = new Associate("Alexandre");
		Associate associateSaved = environmentService.getAssociateRepository().save(associate);

		Agenda agenda = new Agenda("Padrões de Desenvolvimento de Software", "O uso de um padrão de codificação também aumenta a produtividade num projeto, uma vez que a comunicação dentro da equipe de desenvolvimento fica mais fácil.");

		LocalDateTime openUntil = LocalDateTime.now();
		VotingSession votingSession = new VotingSession(agenda, openUntil);
		environmentService.getVotingSessionRepository().save(votingSession);
		Agenda agendaSaved = environmentService.getAgendaRepository().save(agenda);

		String voteRequestAsString = new CreateVoteRequestBuilder()
			.withAssociateId(associateSaved.getId())
			.withAgendaId(agendaSaved.getId())
			.withVote(VoteType.YES)
			.buildJSONString();

		URI voteUri = new URI(votePath);
		mockMvc
			.perform(MockMvcRequestBuilders.put(voteUri).content(voteRequestAsString).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	public void mustReturnStatusCodeOkAfterGetValidVotingSessionResult() throws Exception {
		Agenda agenda = new Agenda("Padrões de Desenvolvimento de Software", "O uso de um padrão de codificação também aumenta a produtividade num projeto, uma vez que a comunicação dentro da equipe de desenvolvimento fica mais fácil.");

		LocalDateTime openUntil = LocalDateTime.now().plusMinutes(5);
		VotingSession votingSession = new VotingSession(agenda, openUntil);

		environmentService.getVotingSessionRepository().save(votingSession);
		Agenda agendaSaved = environmentService.getAgendaRepository().save(agenda);

		HashMap<Associate, VoteType> allAssociateWithYourVotes = new HashMap<>();
		int numberOfAssociateThatWillVoteYes = 5;

		for (int i = 0; i < numberOfAssociateThatWillVoteYes; i++) {
			Associate associate = environmentService.getAssociateRepository().save(new Associate(environmentService.getRandomicString()));
			allAssociateWithYourVotes.put(associate, VoteType.YES);
		}

		int numberOfAssociateThatWillVoteNo = 10;
		for (int i = 0; i < numberOfAssociateThatWillVoteNo; i++) {
			Associate associate = environmentService.getAssociateRepository().save(new Associate(environmentService.getRandomicString()));
			allAssociateWithYourVotes.put(associate, VoteType.NO);
		}

		int totalVotes = numberOfAssociateThatWillVoteYes + numberOfAssociateThatWillVoteNo;

		URI voteUri = new URI(votePath);
		allAssociateWithYourVotes.forEach((associate, vote) -> {
			try {
				String associateVoteAsString = new CreateVoteRequestBuilder()
					.withAssociateId(associate.getId())
					.withAgendaId(agendaSaved.getId())
					.withVote(vote)
					.buildJSONString();
				mockMvc.perform(MockMvcRequestBuilders.put(voteUri).content(associateVoteAsString).contentType(MediaType.APPLICATION_JSON));
			} catch (Exception e) {
				// do nothing
			}
		});
		
		String agendaId = String.valueOf(agendaSaved.getId());
		URI resultVoteUri = new URI(path.concat("/agenda/").concat(agendaId).concat("/result"));

		ResultActions findResultAction = mockMvc
				.perform(MockMvcRequestBuilders.get(resultVoteUri).contentType(MediaType.APPLICATION_JSON));
	
		String responseBody = findResultAction.andReturn().getResponse().getContentAsString();
		VotingSessionResult resultReturned = (VotingSessionResult) new CreateVoteRequestBuilder().parse(responseBody, VotingSessionResult.class);

		assertEquals(totalVotes, resultReturned.getTotalVotes());
		assertEquals(numberOfAssociateThatWillVoteYes, resultReturned.getVotes().get(VoteType.YES));
		assertEquals(numberOfAssociateThatWillVoteNo, resultReturned.getVotes().get(VoteType.NO));
	}

	@Test
	public void mustReturnStatusCodeBadRequestAfterInformedInvalidAgendaIdOnVotingSessionGetResult() throws Exception {
		String agendaId = String.valueOf(0);
		URI resultVoteUri = new URI(path.concat("/agenda/").concat(agendaId).concat("/result"));

		mockMvc
			.perform(MockMvcRequestBuilders.get(resultVoteUri).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
	}

}
