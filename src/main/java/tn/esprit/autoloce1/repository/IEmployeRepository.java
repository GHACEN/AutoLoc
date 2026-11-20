package tn.esprit.autoloce1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloce1.domain.Employe;

public interface IEmployeRepository extends JpaRepository<Employe, Long> {
}