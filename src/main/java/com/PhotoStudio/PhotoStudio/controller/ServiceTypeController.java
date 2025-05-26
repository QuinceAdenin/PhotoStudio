package com.PhotoStudio.PhotoStudio.controller;

import com.PhotoStudio.PhotoStudio.model.ServiceType;
import com.PhotoStudio.PhotoStudio.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/service_types")
public class ServiceTypeController {
    private final ServiceTypeService serviceTypeService; // Внедрение сервиса

    @Autowired
    public ServiceTypeController(ServiceTypeService serviceTypeService) {
        this.serviceTypeService = serviceTypeService;
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("serviceType", new ServiceType());
        return "service_types/form";
    }

    @GetMapping
    public String getAllServiceTypes(Model model) {
        model.addAttribute("serviceTypes", serviceTypeService.findAll());
        return "service_types/list";
    }

    @PostMapping
    public String createServiceType(@ModelAttribute("serviceType") ServiceType serviceType) {
        serviceTypeService.save(serviceType);
        return "redirect:/service_types";
    }

    @GetMapping("/{id}")
    public String viewServiceType(@PathVariable Long id, Model model) {
        model.addAttribute("serviceType", serviceTypeService.findById(id));
        return "service_types/view";
    }

    // Форма редактирования
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("serviceType", serviceTypeService.findById(id));
        return "service_types/form";
    }

    @PostMapping("/{id}")
    public String updateServiceType(
            @PathVariable Long id,
            @ModelAttribute("serviceType") ServiceType serviceType
    ) {
        ServiceType existingServiceType = serviceTypeService.findById(id);
        existingServiceType.setName(serviceType.getName());
        existingServiceType.setDescription(serviceType.getDescription());
        existingServiceType.setDurationHours(serviceType.getDurationHours());

        // Сохраняем существующий ID
        existingServiceType.setId(id);
        serviceTypeService.save(existingServiceType);

        return "redirect:/service_types";
    }

    // Удаление услуги
    @GetMapping("/{id}/delete")
    public String deleteServiceType(@PathVariable Long id) {
        serviceTypeService.deleteById(id);
        return "redirect:/service_types";
    }
}