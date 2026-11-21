package tn.esprit.autoloce1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloce1.domain.Equipement;
import tn.esprit.autoloce1.exception.ResourceNotFoundException;
import tn.esprit.autoloce1.repository.IEquipementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository repository;

    @Override
    public Equipement create(Equipement equipement) {
        if (equipement.getIdEquipement() != null) {
            throw new IllegalArgumentException("Un nouvel equipement ne doit pas avoir d'identifiant");
        }
        validate(equipement);
        return repository.save(equipement);
    }

    @Override
    public Equipement findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
    }

    @Override
    public List<Equipement> findAll() {
        return repository.findAll();
    }

    @Override
    public Equipement update(Long id, Equipement equipement) {
        Equipement existing = findById(id);
        validate(equipement);
        existing.setLibelle(equipement.getLibelle());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Equipement", id);
        }
        repository.deleteById(id);
    }

    private void validate(Equipement equipement) {
        if (equipement.getLibelle() == null || equipement.getLibelle().isBlank()) {
            throw new IllegalArgumentException("Le libelle de l'equipement est obligatoire");
        }
    }
}