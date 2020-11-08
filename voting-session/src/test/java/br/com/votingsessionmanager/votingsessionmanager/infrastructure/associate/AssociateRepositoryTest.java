package br.com.votingsessionmanager.votingsessionmanager.infrastructure.associate;

import static org.junit.jupiter.api.Assertions.*;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.votingsessionmanager.votingsessionmanager.domain.associate.Associate;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AssociateRepositoryTest {

	@Autowired
	private AssociateRepository associateRepository;

	@Test
	public void associateCanBeCreatedWithMinimumSizeValidData() {
		Associate associate = new Associate("A");
		Associate associateSaved = associateRepository.save(associate);

		assertEquals(associateSaved.getName(), "A");
	}

	@Test
	public void associateCanBeCreatedWithAnyValidData() {
		Associate associate = new Associate("Alexandre");
		Associate associateSaved = associateRepository.save(associate);

		assertEquals(associateSaved.getName(), "Alexandre");
	}

	@Test
	public void associateCantBeCreatedBecauseContainNullName() {
		Associate associate = new Associate(null);

		assertThrows(ConstraintViolationException.class, () -> associateRepository.save(associate), "The name field can't be null");
	}

	@Test
	public void associateCantBeCreatedBecauseContainEmptyName() {
		Associate associate = new Associate(null);

		assertThrows(ConstraintViolationException.class, () -> associateRepository.save(associate), "The name field can't be empty");
	}

	@Test
	public void associateCantBeCreatedBecauseContainsALongerNameThanIsSupported() {
		Associate agenda = new Associate("ed Wacky League Antlez Broke the Stereo Neon Tide Bring Back Honesty Coalition Feedback Hand of Aces Keep Going Captain Let’s Pretend Lost State of Dance Paper Taxis Lunar Road Up Down Strange All and I Neon Sheep Eve Hornby Faye Bradley AJ Wilde Michael R");

		assertThrows(ConstraintViolationException.class, () -> associateRepository.save(agenda), "The name field must be no longer than 255 characters");
	}

}
