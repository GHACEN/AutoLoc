package tn.esprit.autoloce1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloce1.domain.Paiement;
import tn.esprit.autoloce1.exception.ResourceNotFoundException;
import tn.esprit.autoloce1.repository.IPaiementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository repository;

    @Override
    public Paiement findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
    }

    @Override
    public List<Paiement> findAll() {
        return repository.findAll();
    }
}