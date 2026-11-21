package tn.esprit.autoloce1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloce1.domain.Employe;
import tn.esprit.autoloce1.exception.ResourceNotFoundException;
import tn.esprit.autoloce1.repository.IEmployeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService{

    private final IEmployeRepository repository;
    @Override
    public Employe create(Employe employe) {
        if(employe.getIdEmploye() != null) {
            throw new IllegalArgumentException("Un nouvel employe ne doit pas avoir d'identifiant");
        }
        validate(employe);
        return repository.save(employe);
    }

    @Override
    public Employe findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
    }

    @Override
    public List<Employe> findAll() {
        return repository.findAll();
    }

    @Override
    public Employe update(Long id, Employe employe) {
        Employe existing = findById(id);
        validate(employe);
        existing.setNom(employe.getNom());
        existing.setPrenom(employe.getPrenom());
        existing.setRole(employe.getRole());
        existing.setAgence(employe.getAgence());
        return repository.save(existing);

    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Employe", id);
        }
        repository.deleteById(id);
    }

    private void validate(Employe employe) {
        if (employe.getNom() == null || employe.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom de l'employe est obligatoire");
        }
        if (employe.getRole() == null) {
            throw new IllegalArgumentException("Le role de l'employe est obligatoire");
        }
    }

}
