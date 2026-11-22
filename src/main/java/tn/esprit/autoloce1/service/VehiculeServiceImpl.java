package tn.esprit.autoloce1.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloce1.domain.Equipement;
import tn.esprit.autoloce1.domain.Maintenance;
import tn.esprit.autoloce1.domain.Vehicule;
import tn.esprit.autoloce1.dto.VehiculeDTO;
import tn.esprit.autoloce1.exception.ResourceNotFoundException;
import tn.esprit.autoloce1.mapper.DtoMapper;
import tn.esprit.autoloce1.repository.IEquipementRepository;
import tn.esprit.autoloce1.repository.IMaintenanceRepository;
import tn.esprit.autoloce1.repository.IVehiculeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository repository;
    private final IEquipementRepository equipementRepository;
    private final IMaintenanceRepository maintenanceRepository;


    @Override
    public Vehicule create(Vehicule vehicule) {
        if (vehicule.getIdVehicule() != null) {
            throw new IllegalArgumentException("Un nouveau vehicule ne doit pas avoir d'identifiant");
        }
        validate(vehicule);
        return repository.save(vehicule);
    }

    @Override
    public Vehicule findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() {
        return repository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        Vehicule existing = findById(id);
        validate(vehicule);
        existing.setMarque(vehicule.getMarque());
        existing.setModele(vehicule.getModele());
        existing.setCategorie(vehicule.getCategorie());
        existing.setImmatriculation(vehicule.getImmatriculation());
        existing.setTarifJournalier(vehicule.getTarifJournalier());
        existing.setStatut(vehicule.getStatut());
        existing.setAgence(vehicule.getAgence());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public VehiculeDTO affecterVehiculeAEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = repository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", idVehicule));
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(()->new ResourceNotFoundException("Equipement", idEquipement));

        if(vehicule.getEquipements() == null) {
            vehicule.setEquipements(new ArrayList<>());
        }
        boolean dejaAffecter = vehicule.getEquipements().stream()
                .anyMatch(e -> e.getIdEquipement().equals(idEquipement));
        if (!dejaAffecter) {
            vehicule.getEquipements().add(equipement);
        }
        Vehicule saved = repository.save(vehicule);
        return DtoMapper.toVehiculeDto(saved, maintenanceRepository.findByVehicule_IdVehicule(saved.getIdVehicule()));
    }

    @Override
    @Transactional
    public VehiculeDTO affecterMaintenanceAvehicule(String description, String immatriculation) {
        if(description == null || description.isEmpty()) {
            throw new IllegalArgumentException("La description est obligatoire");
        }
        if(immatriculation == null || immatriculation.isEmpty()) {
            throw new IllegalArgumentException("L'immatriculation est obligatoire");
        }
        Vehicule vehicule = repository.findByImmatriculation(immatriculation)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule avec l'immatriculation " + immatriculation));
        LocalDate aujourdHui = LocalDate.now();
        Maintenance maintenance = new Maintenance();
        maintenance.setDescription(description);
        maintenance.setDateDebut(aujourdHui);
        maintenance.setDateFin(aujourdHui);
        maintenance.setVehicule(vehicule);
        maintenanceRepository.save(maintenance);

        return DtoMapper.toVehiculeDto(
                vehicule,
                maintenanceRepository.findByVehicule_IdVehicule(vehicule.getIdVehicule())
        );
    }

    private void validate(Vehicule vehicule) {
        if (vehicule.getTarifJournalier() != null
                && vehicule.getTarifJournalier().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le tarif journalier ne peut pas etre negatif");
        }
    }
}