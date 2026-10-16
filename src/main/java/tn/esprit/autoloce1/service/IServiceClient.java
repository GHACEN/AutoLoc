package tn.esprit.autoloce1.service;
import tn.esprit.autoloce1.domain.Client;

import java.util.List;


public interface IServiceClient {
    List<Client> retrieveAllClients();
    Client addClient(Client c);
    Client updateClient(Client c);
    Client retrieveClient(Long idClient);
    void removeClient(Long idClient);
    List<Client> addClients (List<Client> clients);
}

