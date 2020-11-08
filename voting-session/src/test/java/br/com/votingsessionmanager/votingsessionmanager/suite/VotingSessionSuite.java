package br.com.votingsessionmanager.votingsessionmanager.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.com.votingsessionmanager.votingsessionmanager.infrastructure.agenda.AgendaControllerTest;
import br.com.votingsessionmanager.votingsessionmanager.infrastructure.agenda.AgendaRepositoryTest;
import br.com.votingsessionmanager.votingsessionmanager.infrastructure.associate.AssociateControllerTest;
import br.com.votingsessionmanager.votingsessionmanager.infrastructure.associate.AssociateRepositoryTest;
import br.com.votingsessionmanager.votingsessionmanager.infrastructure.votingsession.VotingSessionControllerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	AgendaControllerTest.class,
	AgendaRepositoryTest.class,
	AssociateControllerTest.class,
	AssociateRepositoryTest.class,
	VotingSessionControllerTest.class
})
public class VotingSessionSuite {

}
  