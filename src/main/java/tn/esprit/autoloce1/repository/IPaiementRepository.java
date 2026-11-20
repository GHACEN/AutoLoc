package tn.esprit.autoloce1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloce1.domain.Paiement;

public interface IPaiementRepository extends JpaRepository<Paiement, Long> {
}