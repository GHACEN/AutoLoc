package tn.esprit.autoloce1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import tn.esprit.autoloce1.domain.CategorieVehicule;
import tn.esprit.autoloce1.domain.StatutVehicule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehiculeDTO {
    private Long idVehicule;
    private String marque;
    private String modele;
    private CategorieVehicule categorie;
    private String immatriculation;
    private BigDecimal tarifJournalier;
    private StatutVehicule statut;
    private Long agenceId;
    private List<EquipementDTO>  equipements = new ArrayList<>();
    private List<MaintenanceDTO> maintenances = new ArrayList<>();

}
