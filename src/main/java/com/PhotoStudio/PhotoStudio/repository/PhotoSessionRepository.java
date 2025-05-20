package com.PhotoStudio.PhotoStudio.repository;

import com.PhotoStudio.PhotoStudio.model.PhotoSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PhotoSessionRepository extends JpaRepository<PhotoSession, Long> {
    List<PhotoSession> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    List<PhotoSession> findByStatus(String status);

    @Query("SELECT ps FROM PhotoSession ps " +
            "JOIN FETCH ps.client " +
            "JOIN FETCH ps.photographer " +
            "JOIN FETCH ps.serviceType")
    List<PhotoSession> findAllWithDetails();
}