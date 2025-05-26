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
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Показать всех сотрудников
    @GetMapping
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employees/list";
    }


    // Показать форму создания
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employees/form";
    }

    // Создать сотрудника
    @PostMapping
    public String createEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.save(employee);
        return "redirect:/employees";
    }

    // Показать детали сотрудника
    @GetMapping("/{id}")
    public String viewEmployee(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.findById(id));
        return "employees/view";
    }

    // Показать форму редактирования
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        return "employees/form";
    }

    @PostMapping("/{id}")
    public String updateEmployee(
            @PathVariable Long id,
            @ModelAttribute("employee") Employee employee
    ) {
        Employee existingEmployee = employeeService.findById(id);

        existingEmployee.setFullName(employee.getFullName());
        existingEmployee.setPosition(employee.getPosition());
        existingEmployee.setPhone(employee.getPhone());

        employeeService.save(existingEmployee); // ID остается прежним
        return "redirect:/employees";
    }

    // Удалить сотрудника
    @GetMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
        return "redirect:/employees";
    }
}