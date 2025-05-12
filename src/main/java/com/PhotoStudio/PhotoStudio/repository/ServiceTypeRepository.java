package com.PhotoStudio.PhotoStudio.repository;

import com.PhotoStudio.PhotoStudio.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
}