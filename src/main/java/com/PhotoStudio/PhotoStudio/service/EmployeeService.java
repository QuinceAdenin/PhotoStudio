package com.PhotoStudio.PhotoStudio.service;

import com.PhotoStudio.PhotoStudio.model.Employee;
import com.PhotoStudio.PhotoStudio.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            Long maxId = employeeRepository.findMaxId();
            employee.setId(maxId != null ? maxId + 1 : 1);
        }
        return employeeRepository.save(employee);
    }


    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }


    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> findByPosition(String position) {
        return employeeRepository.findByPosition(position);
    }


}