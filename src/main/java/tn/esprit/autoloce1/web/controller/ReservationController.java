package tn.esprit.autoloce1.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloce1.domain.Reservation;
import tn.esprit.autoloce1.dto.ReservationResponseDTO;
import tn.esprit.autoloce1.dto.ReservationWithContratRequestDTO;
import tn.esprit.autoloce1.service.IReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Tag(name = "Réservations", description = "Gestion des réservations")
public class ReservationController {

    private final IReservationService service;

    @GetMapping
    @Operation(summary = "Lister tous les réservations")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    public List<Reservation> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter une réservation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trouvé"),
            @ApiResponse(responseCode = "404", description = "Introuvable")
    })
    public Reservation findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer une réservation")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public Reservation create(@RequestBody Reservation entity) {
        return service.create(entity);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une réservation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modifié"),
            @ApiResponse(responseCode = "404", description = "Introuvable"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public Reservation update(@Parameter(description = "Identifiant") @PathVariable Long id,
                         @RequestBody Reservation entity) {
        return service.update(id, entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer une réservation")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Supprimé"),
            @ApiResponse(responseCode = "404", description = "Introuvable")
    })
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        service.deleteById(id);
    }

    @PostMapping("/{idReservation}/client")
    @Operation(summary = "Affecter une reservation a un client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Affectation effectuee"),
            @ApiResponse(responseCode = "404", description = "Reservation ou client introuvable"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides")
    })
    public ReservationResponseDTO affecterReservationAClient(
            @Parameter(description = "Identifiant de la reservation")
            @PathVariable
            Long idReservation,

            @Parameter(description = "Nom du client")
            @RequestParam
            String nom,

            @Parameter(description = "Prenom du client")
            @RequestParam
            String prenom)
            {
                return service.affecterReservationAClient(idReservation, nom, prenom);
            }

    @PostMapping("/avec-contrat")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creer une reservation et son contrat associe")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reservation et contrat crees"),
            @ApiResponse(responseCode = "404", description = "Client ou vehicule introuvable"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides")
    })
    public ReservationResponseDTO creerReservationEtContratAssocie(
            @Valid
            @RequestBody
            ReservationWithContratRequestDTO requestDTO)
            {
                return service.creerReservationEtContratAssocie(requestDTO);
            }
}