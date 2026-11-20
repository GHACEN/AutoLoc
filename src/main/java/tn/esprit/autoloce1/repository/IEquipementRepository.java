package tn.esprit.autoloce1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloce1.domain.Equipement;

public interface IEquipementRepository extends JpaRepository<Equipement, Long> {
}