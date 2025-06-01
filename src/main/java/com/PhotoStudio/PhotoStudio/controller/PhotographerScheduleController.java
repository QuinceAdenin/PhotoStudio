package com.PhotoStudio.PhotoStudio.controller;

import com.PhotoStudio.PhotoStudio.model.PhotographerSchedule;
import com.PhotoStudio.PhotoStudio.service.EmployeeService;
import com.PhotoStudio.PhotoStudio.service.PhotographerScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/schedules")
public class PhotographerScheduleController {

    private final PhotographerScheduleService scheduleService;
    private final EmployeeService employeeService;

    @Autowired
    public PhotographerScheduleController(PhotographerScheduleService scheduleService,
                                          EmployeeService employeeService) {
        this.scheduleService = scheduleService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public String listSchedules(Model model) {
        model.addAttribute("schedules", scheduleService.findAll());
        return "schedules/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("schedule", new PhotographerSchedule());
        model.addAttribute("employees", employeeService.findAll());
        return "schedules/form";
    }

    @PostMapping
    public String createSchedule(@ModelAttribute PhotographerSchedule schedule) {
        scheduleService.save(schedule);
        return "redirect:/schedules";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("schedule", scheduleService.findById(id));
        model.addAttribute("employees", employeeService.findAll());
        return "schedules/form";
    }

    @PostMapping("/{id}")
    public String updateSchedule(@PathVariable Long id,
                                 @ModelAttribute PhotographerSchedule schedule) {
        schedule.setId(id);
        scheduleService.save(schedule);
        return "redirect:/schedules";
    }

    @GetMapping("/{id}/delete")
    public String deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteById(id);
        return "redirect:/schedules";
    }
}