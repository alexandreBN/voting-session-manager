package br.com.votingsessionmanager.votingsession.infrastructure.associate;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.votingsessionmanager.votingsession.domain.associate.Associate;

public interface AssociateRepository extends JpaRepository<Associate, Long> {
	Optional<Associate> findByCpf(String cpf);
}
