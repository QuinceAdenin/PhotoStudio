package com.PhotoStudio.PhotoStudio.repository;

import com.PhotoStudio.PhotoStudio.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
    @Query("SELECT MAX(s.id) FROM ServiceType s")
    Long findMaxId();
    @Query("SELECT s FROM ServiceType s ORDER BY s.id ASC")
    List<ServiceType> findAllOrderedById();
}