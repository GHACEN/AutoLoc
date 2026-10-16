package tn.esprit.autoloce1.service;

import tn.esprit.autoloce1.domain.Vehicule;

import java.util.List;

public interface IServiceVehicule {
    List<Vehicule> retrieveAllVehicules();
    Vehicule addVehicule(Vehicule v);
    Vehicule updateVehicule(Vehicule v);
    Vehicule retrieveVehicule(Long idVehicule);
    void removeVehicule(Long idVehicule);
    List<Vehicule> addVehicules(List<Vehicule> vehicules);
}