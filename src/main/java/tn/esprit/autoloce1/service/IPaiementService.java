package tn.esprit.autoloce1.service;

import tn.esprit.autoloce1.domain.Paiement;

import java.util.List;

public interface IPaiementService {
    Paiement findById(Long id);
    List<Paiement> findAll();
}
