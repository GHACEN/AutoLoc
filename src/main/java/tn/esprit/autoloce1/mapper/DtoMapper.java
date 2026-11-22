package tn.esprit.autoloce1.mapper;


import tn.esprit.autoloce1.domain.*;
import tn.esprit.autoloce1.dto.ClientDTO;
import tn.esprit.autoloce1.dto.EquipementDTO;
import tn.esprit.autoloce1.dto.MaintenanceDTO;
import tn.esprit.autoloce1.dto.VehiculeDTO;
import tn.esprit.autoloce1.dto.ContratDTO;
import tn.esprit.autoloce1.dto.ReservationResponseDTO;


import java.util.Collections;
import java.util.ArrayList;
import java.util.List;


public final class DtoMapper {

    private DtoMapper() {
    }

    public static ClientDTO toClientDto(Client client) {
        if(client == null) {
            return null;
        }
        return  new ClientDTO(
                client.getIdClient(),
                client.getNom(),
                client.getPrenom(),
                client.getEmail(),
                client.getTelephone(),
                client.getNumPermis(),
                client.getDateInscription()
               );
    }


    public static EquipementDTO toEquipementDto(Equipement equipement) {
        if(equipement == null) {
            return null;
        }
        return  new EquipementDTO(
                equipement.getIdEquipement(),
                equipement.getLibelle()
        );
    }

    public static MaintenanceDTO toMaintenanceDto(Maintenance maintenance) {
        if (maintenance == null) {
            return null;
        }
        Long vehiculeId = maintenance.getVehicule() != null
                ? maintenance.getVehicule().getIdVehicule()
                : null;
        return  new MaintenanceDTO(
                maintenance.getIdMaintenance(),
                maintenance.getDateDebut(),
                maintenance.getDateFin(),
                maintenance.getDescription(),
                vehiculeId
        );
    }

    public static VehiculeDTO toVehiculeDto(Vehicule vehicule) {
        return toVehiculeDto(vehicule, Collections.emptyList());
    }

    public static VehiculeDTO toVehiculeDto(Vehicule vehicule, List<Maintenance> maintenances) {
        if (vehicule == null) {
            return null;
        }

        Long agenceId = vehicule.getAgence() != null
                ? vehicule.getAgence().getIdAgence()
                : null;

        List<EquipementDTO> equipements = new ArrayList<>();
        if (vehicule.getEquipements() != null) {
            for (Equipement equipement : vehicule.getEquipements()) {
                EquipementDTO dto = toEquipementDto(equipement);
                if (dto != null) {
                    equipements.add(dto);
                }
            }
        }

        List<MaintenanceDTO> maintenanceDtos = new ArrayList<>();
        if (maintenances != null) {
            for (Maintenance maintenance : maintenances) {
                MaintenanceDTO dto = toMaintenanceDto(maintenance);
                if (dto != null) {
                    maintenanceDtos.add(dto);
                }
            }
        }

        return new VehiculeDTO(
                vehicule.getIdVehicule(),
                vehicule.getMarque(),
                vehicule.getModele(),
                vehicule.getCategorie(),
                vehicule.getImmatriculation(),
                vehicule.getTarifJournalier(),
                vehicule.getStatut(),
                agenceId,
                equipements,
                maintenanceDtos
        );
    }

    public static ContratDTO toContratDto(Contrat contrat) {
        if (contrat == null) {
            return null;
        }
        Long reservationId = contrat.getReservation() != null
                ? contrat.getReservation().getIdReservation()
                : null;
        return new ContratDTO(
                contrat.getIdContrat(),
                contrat.getDateSignature(),
                contrat.getMontantTotal(),
                contrat.isValide(),
                reservationId
        );
    }

    public static ReservationResponseDTO toReservationResponseDto(Reservation reservation) {
        if (reservation == null) {
            return null;
        }

        Long vehiculeId = reservation.getVehicule() != null
                ? reservation.getVehicule().getIdVehicule()
                : null;

        ContratDTO contrat = reservation.getContrat() != null
                ? toContratDto(reservation.getContrat())
                : null;

        return new ReservationResponseDTO(
                reservation.getIdReservation(),
                reservation.getDateDebut(),
                reservation.getDateFin(),
                reservation.getStatut(),
                toClientDto(reservation.getClient()),
                vehiculeId,
                contrat
        );
    }
}

