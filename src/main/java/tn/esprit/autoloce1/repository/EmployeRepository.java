package tn.esprit.autoloce1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloce1.domain.Employe;

public interface EmployeRepository extends JpaRepository<Employe, Long> {
}