package tn.esprit.autoloce1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloce1.domain.Contrat;

public interface ContratRepository extends JpaRepository<Contrat, Long> {
}