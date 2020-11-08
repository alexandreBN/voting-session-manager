package br.com.votingsessionmanager.votingsession.infrastructure.agenda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.votingsessionmanager.votingsession.domain.agenda.Agenda;
import br.com.votingsessionmanager.votingsession.infrastructure.agenda.AgendaRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AgendaRepositoryTest {

	@Autowired
	private AgendaRepository agendaRepository;

	@Test
	public void agendaCanBeCreatedWithMinimumSizeValidDataWhereDescriptionIsEmpty() {
		Agenda agenda = new Agenda("P", "");
		Agenda agendaSaved = agendaRepository.save(agenda);

		assertEquals(agendaSaved.getName(), "P");
		assertEquals(agendaSaved.getDescription(), "");
	}

	@Test
	public void agendaCanBeCreatedWithMinimumSizeValidDataWhereDescriptionIsNull() {
		Agenda agenda = new Agenda("P", null);
		Agenda agendaSaved = agendaRepository.save(agenda);

		assertEquals(agendaSaved.getName(), "P");
		assertEquals(agendaSaved.getDescription(), null);
	}

	@Test
	public void agendaCanBeCreatedWithAnyValidData() {
		Agenda agenda = new Agenda("Padrões de Desenvolvimento de Software", "O uso de um padrão de codificação também aumenta a produtividade num projeto, uma vez que a comunicação dentro da equipe de desenvolvimento fica mais fácil.");
		Agenda agendaSaved = agendaRepository.save(agenda);

		assertEquals(agendaSaved.getName(), "Padrões de Desenvolvimento de Software");
		assertEquals(agendaSaved.getDescription(), "O uso de um padrão de codificação também aumenta a produtividade num projeto, uma vez que a comunicação dentro da equipe de desenvolvimento fica mais fácil.");
	}

	@Test
	public void agendaCantBeCreatedBecauseContainNullName() {
		Agenda agenda = new Agenda(null, "O");

		assertThrows(ConstraintViolationException.class, () -> agendaRepository.save(agenda), "The name field can't be null");
	}

	@Test
	public void agendaCantBeCreatedBecauseContainEmptyName() {
		Agenda agenda = new Agenda("", "O");

		assertThrows(ConstraintViolationException.class, () -> agendaRepository.save(agenda), "The name field can't be empty");
	}

	@Test
	public void agendaCantBeCreatedBecauseContainsALongerNameThanIsSupported() {
		Agenda agenda = new Agenda("Padrões de Desenvolvimento de Software Padrões de Desenvolvimento de Software Padrões de Desenvolvimento de SoftwarePadrões de Desenvolvimento de Software Padrões de Desenvolvimento de Software Padrões de Desenvolvimento de Software Padrões de Desenvolvime", "O");

		assertThrows(ConstraintViolationException.class, () -> agendaRepository.save(agenda), "The name field must be no longer than 255 characters");
	}

	@Test
	public void agendaCantBeCreatedBecauseContainsALongerDescriptionThanIsSupported() {
		Agenda agenda = new Agenda("Padrões de Desenvolvimento de Software", "O uso de um padrão de codificação também aumenta a produtividade num projeto, uma vez que a comunicação dentro da equipe de desenvolvimento fica mais fácil, mas vale ressaltar que partes desses padrões são vistas, algumas vezes, como sugestões por empresas");

		assertThrows(ConstraintViolationException.class, () -> agendaRepository.save(agenda), "The description field must be no longer than 255 characters");
	}

}
