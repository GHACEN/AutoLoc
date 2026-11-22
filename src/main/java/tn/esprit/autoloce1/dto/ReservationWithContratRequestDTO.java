package tn.esprit.autoloce1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationWithContratRequestDTO {
    @NotNull(message = "clientId est obligatoire")
    private Long clientId;

    @NotNull(message = "vehiculeId est obligatoire")
    private Long vehiculeId;

    @NotNull(message = "dateDebut est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "dateFin est obligatoire")
    private LocalDate dateFin;

    @NotNull(message = "dateSignature est obligatoire")
    private LocalDate dateSignature;
}