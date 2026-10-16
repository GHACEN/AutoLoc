package tn.esprit.autoloce1.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloce1.domain.Client;
import tn.esprit.autoloce1.repository.ClientRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceClient implements IServiceClient {
    private final ClientRepository repo;
    @Override
    public List<Client> retrieveAllClients() {
        return repo.findAll();
    }

    @Override
    public Client addClient(Client c) {
        return repo.save(c);
    }

    @Override
    public Client updateClient(Client c) {
        return repo.save(c);
    }

    @Override
    public Client retrieveClient(Long idClient) {
        return repo.findById(idClient).orElse(null);
    }

    @Override
    public void removeClient(Long idClient) {
        repo.deleteById(idClient);
    }

    @Override
    public List<Client> addClients(List<Client> clients) {
        return repo.saveAll(clients);
    }
}
