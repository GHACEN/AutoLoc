package tn.esprit.autoloce1.controller;

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
import tn.esprit.autoloce1.service.IClientService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final IClientService cs;

    @GetMapping
    public List<Client> getAllClients() {
        return cs.findAll();
    }

    @GetMapping("/{idClient}")
    public Client getClient(@PathVariable Long idClient) {
        return cs.findById(idClient);
    }

    @PostMapping
    public Client addClient(@RequestBody Client c) {
        return cs.create(c);
    }

    @PutMapping("/{idClient}")
    public Client updateClient(@PathVariable Long idClient, @RequestBody Client c) {
        return cs.update(idClient, c);
    }

    @DeleteMapping("/{idClient}")
    public void removeClient(@PathVariable Long idClient) {
        cs.deleteById(idClient);
    }

    @PostMapping("/multiple")
    public List<Client> addClients(@RequestBody List<Client> clients) {
        return clients.stream().map(cs::create).toList();
    }
}