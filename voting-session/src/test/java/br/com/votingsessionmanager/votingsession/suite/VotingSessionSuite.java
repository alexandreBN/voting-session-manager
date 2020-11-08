package br.com.votingsessionmanager.votingsession.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.com.votingsessionmanager.votingsession.infrastructure.agenda.AgendaControllerTest;
import br.com.votingsessionmanager.votingsession.infrastructure.agenda.AgendaRepositoryTest;
import br.com.votingsessionmanager.votingsession.infrastructure.associate.AssociateControllerTest;
import br.com.votingsessionmanager.votingsession.infrastructure.associate.AssociateRepositoryTest;
import br.com.votingsessionmanager.votingsession.infrastructure.votingsession.VotingSessionControllerTest;

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
  