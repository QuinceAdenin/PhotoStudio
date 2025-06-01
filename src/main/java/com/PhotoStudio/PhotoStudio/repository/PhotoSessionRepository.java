package com.PhotoStudio.PhotoStudio.repository;

import com.PhotoStudio.PhotoStudio.model.PhotoSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface PhotoSessionRepository extends JpaRepository<PhotoSession, Long> {
    List<PhotoSession> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    List<PhotoSession> findByStatus(String status);
    List<PhotoSession> findByPhotographerId(Long photographerId);
    @Query("SELECT MAX(ps.id) FROM PhotoSession ps WHERE ps.status <> 'deleted'")
    Long findMaxActiveId();
    @Query("SELECT MAX(ps.id) FROM PhotoSession ps")
    Long findMaxId();
    @Query("SELECT ps FROM PhotoSession ps " +
            "JOIN FETCH ps.client " +
            "JOIN FETCH ps.photographer " +
            "JOIN FETCH ps.serviceType")
    List<PhotoSession> findAllWithDetails();
    @Query("SELECT ps FROM PhotoSession ps ORDER BY ps.id ASC")
    List<PhotoSession> findAllOrderedById();
    // PhotoSessionRepository.java
    @Query(value = "SELECT ps.* FROM photo_sessions ps " +
            "JOIN service_types st ON ps.service_type_id = st.id " +
            "WHERE ps.employee_id = :employeeId " +
            "AND ps.start_time < :endTime " +
            "AND (ps.start_time + (st.duration * interval '1 hour')) > :startTime " +
            "AND ps.id <> :excludeSessionId",
            nativeQuery = true)
    List<PhotoSession> findConflictingSessions(
            @Param("employeeId") Long employeeId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("excludeSessionId") Long excludeSessionId
    );


}