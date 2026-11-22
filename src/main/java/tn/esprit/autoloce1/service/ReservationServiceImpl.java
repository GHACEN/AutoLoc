package tn.esprit.autoloce1.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloce1.domain.*;
import tn.esprit.autoloce1.dto.ReservationResponseDTO;
import tn.esprit.autoloce1.dto.ReservationWithContratRequestDTO;
import tn.esprit.autoloce1.exception.ResourceNotFoundException;
import tn.esprit.autoloce1.mapper.DtoMapper;
import tn.esprit.autoloce1.repository.IClientRepository;
import tn.esprit.autoloce1.repository.IContratRepository;
import tn.esprit.autoloce1.repository.IReservationRepository;
import tn.esprit.autoloce1.repository.IVehiculeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository repository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IContratRepository contratRepository;

    @Override
    public Reservation create(Reservation reservation) {
        if (reservation.getIdReservation() != null) {
            throw new IllegalArgumentException("Une nouvelle reservation ne doit pas avoir d'identifiant");
        }
        validate(reservation);
        return repository.save(reservation);
    }

    @Override
    public Reservation findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
    }

    @Override
    public List<Reservation> findAll() {
        return repository.findAll();
    }

    @Override
    public Reservation update(Long id, Reservation reservation) {
        Reservation existing = findById(id);
        validate(reservation);
        existing.setDateDebut(reservation.getDateDebut());
        existing.setDateFin(reservation.getDateFin());
        existing.setStatut(reservation.getStatut());
        existing.setClient(reservation.getClient());
        existing.setVehicule(reservation.getVehicule());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public ReservationResponseDTO affecterReservationAClient(Long idReservation, String nom, String prenom) {
        if(nom == null || nom.isEmpty() || prenom == null || prenom.isEmpty()) {
            throw new IllegalArgumentException("Le nom et le prenom du client sont obligatoire");
        }
        Reservation reservation = findById(idReservation);
        Client client = clientRepository.findByNomAndPrenom(nom, prenom)
                .orElseThrow(()->new ResourceNotFoundException("Client " + nom + " " + prenom));
        reservation.setClient(client);

        return DtoMapper.toReservationResponseDto(repository.save(reservation));
    }

    @Override
    @Transactional
    public ReservationResponseDTO creerReservationEtContratAssocie(ReservationWithContratRequestDTO requestDTO) {
        if(requestDTO == null) {
            throw new IllegalArgumentException("La requete est obligatoire");
        }
        if(requestDTO.getClientId() == null) {
            throw new IllegalArgumentException("clientId est obligatoire");
        }
        if(requestDTO.getVehiculeId() == null) {
            throw new IllegalArgumentException("vehiculeId est obligatoire");
        }
        if(requestDTO.getDateSignature() == null) {
            throw new IllegalArgumentException("dateSignature est obligatoire");
        }
        validateDates(requestDTO.getDateDebut(), requestDTO.getDateFin());
        Client client = clientRepository.findById(requestDTO.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client", requestDTO.getClientId()));
        Vehicule vehicule = vehiculeRepository.findById(requestDTO.getVehiculeId())
                .orElseThrow(()->new ResourceNotFoundException("Vehicule", requestDTO.getVehiculeId()));
        if(vehicule.getTarifJournalier() == null) {
            throw new IllegalArgumentException("tarifJournalier est obligatoire");
        }
        Reservation reservation = new Reservation();
        reservation.setDateDebut(requestDTO.getDateDebut());
        reservation.setDateFin(requestDTO.getDateFin());
        reservation.setStatut(StatutReservation.CONFIRMEE);
        reservation.setClient(client);
        reservation.setVehicule(vehicule);
        Reservation savedReservation = repository.save(reservation);

        Contrat contrat = new Contrat();
        contrat.setDateSignature(requestDTO.getDateSignature());
        contrat.setMontantTotal(calculerMontantTotal(requestDTO.getDateDebut(), requestDTO.getDateFin(), vehicule.getTarifJournalier()));
        contrat.setValide(true);
        contrat.setReservation(savedReservation);
        Contrat savedContrat = contratRepository.save(contrat);

        savedReservation.setContrat(savedContrat);
        return DtoMapper.toReservationResponseDto(savedReservation);
    }

    private void validate(Reservation reservation) {
        if (reservation.getDateDebut() != null && reservation.getDateFin() != null
                && reservation.getDateFin().isBefore(reservation.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas etre anterieure a la date de debut");
        }
    }

    private void validateDates(LocalDate dateDebut, LocalDate dateFin) {
        if (dateDebut == null || dateFin == null) {
            throw new IllegalArgumentException("Les dates de reservation sont obligatoires");
        }
        if (dateFin.isBefore(dateDebut)) {
            throw new IllegalArgumentException("La date de fin ne peut pas etre anterieure a la date de debut");
        }
    }

    private BigDecimal calculerMontantTotal(LocalDate dateDebut, LocalDate dateFin, BigDecimal tarifJournalier) {
        long jours = Math.max(1, ChronoUnit.DAYS.between(dateDebut, dateFin));
        return tarifJournalier.multiply(BigDecimal.valueOf(jours));
    }
}