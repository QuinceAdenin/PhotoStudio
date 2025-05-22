package com.PhotoStudio.PhotoStudio.service;

import com.PhotoStudio.PhotoStudio.model.Client;
import com.PhotoStudio.PhotoStudio.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    public List<Client> searchByName(String keyword) {
        return clientRepository.findByFullNameContainingIgnoreCase(keyword);
    }
}