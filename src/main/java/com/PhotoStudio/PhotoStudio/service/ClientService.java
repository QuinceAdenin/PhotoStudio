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

    public Client save(Client client) {
        // Проверка уникальности для новых клиентов (не для редактирования)
        if (client.getId() == null) {
            // Проверка email
            Client existingEmail = clientRepository.findByEmail(client.getEmail());
            if (existingEmail != null) {
                throw new RuntimeException("Email уже существует");
            }

            // Проверка телефона
            Client existingPhone = clientRepository.findByPhone(client.getPhone());
            if (existingPhone != null) {
                throw new RuntimeException("Телефон уже существует");
            }

            // Генерация ID
            Long maxId = clientRepository.findMaxId();
            client.setId(maxId != null ? maxId + 1 : 1);
        }

        return clientRepository.save(client);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    public List<Client> searchByName(String keyword) {
        return clientRepository.findByFullNameContainingIgnoreCase(keyword);
    }
}