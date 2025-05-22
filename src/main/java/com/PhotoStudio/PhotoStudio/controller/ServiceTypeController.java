package com.PhotoStudio.PhotoStudio.controller;

import com.PhotoStudio.PhotoStudio.model.ServiceType;
import com.PhotoStudio.PhotoStudio.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/service-types")
public class ServiceTypeController {
    private final ServiceTypeService serviceTypeService; // Внедрение сервиса

    @Autowired
    public ServiceTypeController(ServiceTypeService serviceTypeService) {
        this.serviceTypeService = serviceTypeService;
    }

    @GetMapping
    public String getAllServiceTypes(Model model) {
        model.addAttribute("serviceTypes", serviceTypeService.findAll());
        return "service-types/list";
    }

    @PostMapping
    public String createServiceType(@ModelAttribute("serviceType") ServiceType serviceType) {
        serviceTypeService.save(serviceType);
        return "redirect:/service-types";
    }

    @GetMapping("/{id}")
    public String viewServiceType(@PathVariable Long id, Model model) {
        model.addAttribute("serviceType", serviceTypeService.findById(id));
        return "service-types/view";
    }

    // Остальные методы используют serviceTypeService
}