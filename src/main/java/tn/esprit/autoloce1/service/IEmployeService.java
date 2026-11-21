package tn.esprit.autoloce1.service;

import tn.esprit.autoloce1.domain.Employe;

import java.util.List;

public interface IEmployeService {
    Employe create(Employe employe);
    Employe findById(Long id);
    List<Employe> findAll();
    Employe update(Long id, Employe employe);
    void deleteById(Long id);

}
