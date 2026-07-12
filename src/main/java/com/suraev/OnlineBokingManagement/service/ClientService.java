package com.suraev.OnlineBokingManagement.service;

import com.suraev.OnlineBokingManagement.entities.Client;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> getAllClients();
    Client addClient(Client client);
    void deleteClient(Long id);
    Optional<Client> getClient(Long id);
}
