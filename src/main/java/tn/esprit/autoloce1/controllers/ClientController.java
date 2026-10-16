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
import tn.esprit.autoloce1.domain.Client;
import tn.esprit.autoloce1.service.IServiceClient;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final IServiceClient cs;

    @GetMapping()
    public List<Client> getAllClients(){
        return cs.retrieveAllClients();
    }

    @GetMapping("/{idClient}")
    public Client getClient(@PathVariable Long idClient){
        return cs.retrieveClient(idClient);
    }

    @PostMapping()
    public Client addClient(@RequestBody Client c){
        return cs.addClient(c);
    }

    @PutMapping("/{idClient}")
    public Client updateClient(@PathVariable Long idClient, @RequestBody Client c){
        c.setIdClient(idClient);
        return cs.updateClient(c);
    }

    @DeleteMapping("/{idClient}")
    public void removeClient(@PathVariable Long idClient){
        cs.removeClient(idClient);
    }

    @PostMapping("/multiple")
    public List<Client> addClients(@RequestBody List<Client> clients){
        return cs.addClients(clients);
    }

}