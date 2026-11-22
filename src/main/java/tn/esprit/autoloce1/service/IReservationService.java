package tn.esprit.autoloce1.service;

import tn.esprit.autoloce1.domain.Reservation;
import tn.esprit.autoloce1.dto.ReservationResponseDTO;
import tn.esprit.autoloce1.dto.ReservationWithContratRequestDTO;

import java.util.List;

public interface IReservationService {
    Reservation create(Reservation reservation);
    Reservation findById(Long id);
    List<Reservation> findAll();
    Reservation update(Long id, Reservation reservation);
    void deleteById(Long id);

    ReservationResponseDTO affecterReservationAClient(Long idReservation, String nom, String prenom);
    ReservationResponseDTO creerReservationEtContratAssocie(ReservationWithContratRequestDTO requestDTO);
}
