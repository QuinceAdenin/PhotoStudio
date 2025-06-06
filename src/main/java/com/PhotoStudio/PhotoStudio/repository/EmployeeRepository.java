package com.PhotoStudio.PhotoStudio.repository;

import com.PhotoStudio.PhotoStudio.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByPosition(String position); // Для поиска фотографов
    @Query("SELECT MAX(e.id) FROM Employee e")
    Long findMaxId();
    @Query("SELECT e FROM Employee e ORDER BY e.id ASC")
    List<Employee> findAllOrderedById();
}
