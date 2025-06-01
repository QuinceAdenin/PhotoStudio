package com.PhotoStudio.PhotoStudio.controller;

import com.PhotoStudio.PhotoStudio.service.ClientService;
import com.PhotoStudio.PhotoStudio.service.EmployeeService;
import com.PhotoStudio.PhotoStudio.service.PhotoSessionService;
import com.PhotoStudio.PhotoStudio.service.PhotographerScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final PhotoSessionService photoSessionService;
    private final ClientService clientService;
    private final EmployeeService employeeService;
    private final PhotographerScheduleService scheduleService;

    @Autowired
    public HomeController(
            PhotoSessionService photoSessionService,
            ClientService clientService,
            EmployeeService employeeService,
            PhotographerScheduleService scheduleService
    ) {
        this.photoSessionService = photoSessionService;
        this.clientService = clientService;
        this.employeeService = employeeService;
        this.scheduleService = scheduleService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("sessionCount", photoSessionService.findAll().size());
        model.addAttribute("clientCount", clientService.findAll().size());
        model.addAttribute("employeeCount", employeeService.findAll().size());
        model.addAttribute("scheduleCount", scheduleService.countAll());
        return "home";
    }
}