package tn.esprit.autoloce1.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloce1.domain.Contrat;
import tn.esprit.autoloce1.domain.Reservation;
import tn.esprit.autoloce1.domain.Vehicule;
import tn.esprit.autoloce1.dto.ContratDTO;
import tn.esprit.autoloce1.exception.ResourceNotFoundException;
import tn.esprit.autoloce1.mapper.DtoMapper;
import tn.esprit.autoloce1.repository.IContratRepository;
import tn.esprit.autoloce1.repository.IReservationRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository repository;
    private final IReservationRepository reservationRepository;


    @Override
    public Contrat create(Contrat contrat) {
        if (contrat.getIdContrat() != null) {
            throw new IllegalArgumentException("Un nouveau contrat ne doit pas avoir d'identifiant");
        }
        validate(contrat);
        return repository.save(contrat);
    }

    @Override
    public Contrat findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }

    @Override
    public List<Contrat> findAll() {
        return repository.findAll();
    }

    @Override
    public Contrat update(Long id, Contrat contrat) {
        Contrat existing = findById(id);
        validate(contrat);
        existing.setDateSignature(contrat.getDateSignature());
        existing.setMontantTotal(contrat.getMontantTotal());
        existing.setValide(contrat.isValide());
        existing.setReservation(contrat.getReservation());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin) {
        if (dateSignature == null) {
            throw new IllegalArgumentException("La date de signature est obligatoire");
        }
        if (dateDebut == null || dateFin == null) {
            throw new IllegalArgumentException("Les dates de reservation sont obligatoires");
        }
        if (dateFin.isBefore(dateDebut)) {
            throw new IllegalArgumentException("La date de fin ne peut pas etre anterieure a la date de debut");
        }

        List<Reservation> reservations = reservationRepository.findByDateDebutAndDateFin(dateDebut, dateFin);
        if (reservations.isEmpty()) {
            throw new ResourceNotFoundException("Reservation avec dates " + dateDebut + " / " + dateFin);
        }
        if (reservations.size() > 1) {
            throw new IllegalArgumentException("Plusieurs reservations correspondent a ces dates");
        }

        Reservation reservation = reservations.get(0);
        Vehicule vehicule = reservation.getVehicule();
        if (vehicule == null || vehicule.getTarifJournalier() == null) {
            throw new IllegalArgumentException("Le tarif journalier du vehicule est obligatoire");
        }

        Contrat contrat = new Contrat();
        contrat.setDateSignature(dateSignature);
        contrat.setMontantTotal(calculerMontantTotal(dateDebut, dateFin, vehicule.getTarifJournalier()));
        contrat.setValide(true);
        contrat.setReservation(reservation);

        Contrat saved = repository.save(contrat);
        reservation.setContrat(saved);
        return DtoMapper.toContratDto(saved);
    }

    private void validate(Contrat contrat) {
        if (contrat.getMontantTotal() != null
                && contrat.getMontantTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le montant total ne peut pas etre negatif");
        }
    }

    private BigDecimal calculerMontantTotal(LocalDate dateDebut, LocalDate dateFin, BigDecimal tarifJournalier) {
        long jours = Math.max(1, ChronoUnit.DAYS.between(dateDebut, dateFin));
        return tarifJournalier.multiply(BigDecimal.valueOf(jours));
    }

}