package tn.esprit.autoloce1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloce1.domain.Agence;

public interface AgenceRepository extends JpaRepository<Agence, Long> {
}