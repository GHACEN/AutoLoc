package tn.esprit.autoloce1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloce1.domain.Vehicule;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
}