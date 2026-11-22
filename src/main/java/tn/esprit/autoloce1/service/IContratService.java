package tn.esprit.autoloce1.service;

import tn.esprit.autoloce1.domain.Contrat;
import tn.esprit.autoloce1.dto.ContratDTO;

import java.time.LocalDate;
import java.util.List;

public interface IContratService {
    Contrat create(Contrat contrat);
    Contrat findById(Long id);
    List<Contrat> findAll();
    Contrat update(Long id, Contrat contrat);
    void deleteById(Long id);

    ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin);
}
