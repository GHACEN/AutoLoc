package tn.esprit.autoloce1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autoloce1.domain.StatutReservation;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDTO {
    private Long idReservation;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutReservation  statut;
    private ClientDTO client;
    private Long vehiculeId;
    private ContratDTO contrat;
}
