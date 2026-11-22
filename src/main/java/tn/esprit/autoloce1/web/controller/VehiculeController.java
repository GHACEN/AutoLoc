package tn.esprit.autoloce1.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloce1.domain.Vehicule;
import tn.esprit.autoloce1.dto.VehiculeDTO;
import tn.esprit.autoloce1.service.IVehiculeService;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
@Tag(name = "Véhicules", description = "Gestion des véhicules")
public class VehiculeController {

    private final IVehiculeService service;

    @GetMapping
    @Operation(summary = "Lister tous les véhicules")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    public List<Vehicule> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter une véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trouvé"),
            @ApiResponse(responseCode = "404", description = "Introuvable")
    })
    public Vehicule findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer une véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public Vehicule create(@RequestBody Vehicule entity) {
        return service.create(entity);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modifié"),
            @ApiResponse(responseCode = "404", description = "Introuvable"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public Vehicule update(@Parameter(description = "Identifiant") @PathVariable Long id,
                         @RequestBody Vehicule entity) {
        return service.update(id, entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer une véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Supprimé"),
            @ApiResponse(responseCode = "404", description = "Introuvable")
    })
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        service.deleteById(id);
    }

    @PostMapping("/{idVehicule}/equipements/{idEquipement}")
    @Operation(summary = "Affecter un vehicule a un equipement")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Affectation effectuee"),
            @ApiResponse(responseCode = "404", description = "Vehicule ou equipement introuvable")
    })
    public VehiculeDTO affecterVehiculeAEquipement(
            @Parameter(description = "id du vehicule")
            @PathVariable
            Long idVehicule,

            @Parameter(description = "id du l'equipement")
            @PathVariable
            Long idEquipement
    ) {
        return service.affecterVehiculeAEquipement(idVehicule, idEquipement);
    }

    @PostMapping("/maintenance")
    @Operation(summary = "Affecter une maintenance a un vehicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance creee"),
            @ApiResponse(responseCode = "404", description = "Vehicule introuvable"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides")
    })
    public VehiculeDTO affecterMaintenanceAvehicule(
            @Parameter(description = "Description")
            @RequestParam
            String description,

            @Parameter(description = "Immatriculation")
            @RequestParam
            String immatriculation) {
        return service.affecterMaintenanceAvehicule(description, immatriculation);
    }

}