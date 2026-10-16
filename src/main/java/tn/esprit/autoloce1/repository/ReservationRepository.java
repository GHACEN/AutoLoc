package tn.esprit.autoloce1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloce1.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}