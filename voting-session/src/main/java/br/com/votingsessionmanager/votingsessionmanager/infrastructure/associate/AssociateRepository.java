package br.com.votingsessionmanager.votingsessionmanager.infrastructure.associate;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.votingsessionmanager.votingsessionmanager.domain.associate.Associate;

public interface AssociateRepository extends JpaRepository<Associate, Long>{

}
