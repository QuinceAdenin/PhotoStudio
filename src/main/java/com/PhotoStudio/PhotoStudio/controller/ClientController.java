package com.PhotoStudio.PhotoStudio.controller;

import com.PhotoStudio.PhotoStudio.model.Client;
import com.PhotoStudio.PhotoStudio.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String getAllClients(Model model) {
        model.addAttribute("clients", clientService.findAll());
        return "clients/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("client", new Client());
        return "clients/form";
    }

    @PostMapping
    public String createClient(@ModelAttribute("client") Client client) {
        clientService.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("client", clientService.findById(id));
        return "clients/form";
    }

    @PostMapping("/{id}")
    public String updateClient(@PathVariable Long id, @ModelAttribute("client") Client client) {
        client.setId(id);
        clientService.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/{id}/delete")
    public String deleteClient(@PathVariable Long id) {
        clientService.deleteById(id);
        return "redirect:/clients";
    }

    @GetMapping("/{id}")
    public String viewClient(@PathVariable Long id, Model model) {
        model.addAttribute("client", clientService.findById(id));
        return "clients/view";
    }
}