package tn.esprit.autoloce1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloce1.domain.Reservation;
import tn.esprit.autoloce1.exception.ResourceNotFoundException;
import tn.esprit.autoloce1.repository.IReservationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository repository;

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

    private void validate(Reservation reservation) {
        if (reservation.getDateDebut() != null && reservation.getDateFin() != null
                && reservation.getDateFin().isBefore(reservation.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas etre anterieure a la date de debut");
        }
    }
}