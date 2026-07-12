package com.suraev.OnlineBokingManagement.service;

import com.suraev.OnlineBokingManagement.entities.Client;
import com.suraev.OnlineBokingManagement.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Optional<Client> getClient(Long id) {
        return clientRepository.getClientById(id);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    @Transactional
    @Override
    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
