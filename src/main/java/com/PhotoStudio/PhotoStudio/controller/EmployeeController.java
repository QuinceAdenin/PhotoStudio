package com.PhotoStudio.PhotoStudio.controller;

import com.PhotoStudio.PhotoStudio.model.Employee;
import com.PhotoStudio.PhotoStudio.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService; // Внедрение сервиса

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employees/list";
    }

    @PostMapping
    public String createEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/{id}")
    public String viewEmployee(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.findById(id));
        return "employees/view";
    }

    // Остальные методы (редактирование, удаление) аналогично используют employeeService
}