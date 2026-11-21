package tn.esprit.autoloce1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloce1.domain.Agence;
import tn.esprit.autoloce1.exception.ResourceNotFoundException;
import tn.esprit.autoloce1.repository.IAgenceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {
    private final IAgenceRepository repository;

    @Override
    public Agence create(Agence agence) {
        if (agence.getIdAgence() != null) {
            throw new IllegalArgumentException("Une nouvelle agence ne doit pas avoir d'identifiant");
        }
        validate(agence);
        return repository.save(agence);
    }

    @Override
    public Agence findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }

    @Override
    public List<Agence> findAll() {
        return repository.findAll();
    }

    @Override
    public Agence update(Long id, Agence agence) {
        Agence existing = findById(id);
        validate(agence);
        existing.setNom(agence.getNom());
        existing.setVille(agence.getVille());
        existing.setAdresse(agence.getAdresse());
        existing.setTelephone(agence.getTelephone());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", id);
        }
        repository.deleteById(id);
    }

    private void validate(Agence agence) {
        if (agence.getNom() == null || agence.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom de l'agence est obligatoire");
        }
        if (agence.getVille() == null || agence.getVille().isBlank()) {
            throw new IllegalArgumentException("La ville de l'agence est obligatoire");
        }
        if (agence.getTelephone() == null || agence.getTelephone().isBlank()) {
            throw new IllegalArgumentException("Le telephone de l'agence est obligatoire");
        }
    }
}
