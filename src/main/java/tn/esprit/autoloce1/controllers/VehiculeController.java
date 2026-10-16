package tn.esprit.autoloce1.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloce1.domain.Vehicule;
import tn.esprit.autoloce1.service.IServiceVehicule;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/vehicule")
public class VehiculeController {

    private final IServiceVehicule vs;

    @GetMapping()
    public List<Vehicule> getAllVehicules(){
        return vs.retrieveAllVehicules();
    }

    @GetMapping("/{idVehicule}")
    public Vehicule getVehicule(@PathVariable Long idVehicule){
        return vs.retrieveVehicule(idVehicule);
    }

    @PostMapping()
    public Vehicule addVehicule(@RequestBody Vehicule v){
        return vs.addVehicule(v);
    }

    @PutMapping("/{idVehicule}")
    public Vehicule updateVehicule(@PathVariable Long idVehicule, @RequestBody Vehicule v){
        v.setIdVehicule(idVehicule);
        return vs.updateVehicule(v);
    }

    @DeleteMapping("/{idVehicule}")
    public void removeVehicule(@PathVariable Long idVehicule){
        vs.removeVehicule(idVehicule);
    }

    @PostMapping("/multiple")
    public List<Vehicule> addVehicules(@RequestBody List<Vehicule> vehicules){
        return vs.addVehicules(vehicules);
    }

}