package tn.esprit.autoloce1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloce1.domain.Maintenance;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
}