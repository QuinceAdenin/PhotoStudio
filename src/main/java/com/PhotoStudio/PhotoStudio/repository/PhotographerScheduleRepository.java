// PhotographerScheduleRepository.java
package com.PhotoStudio.PhotoStudio.repository;

import com.PhotoStudio.PhotoStudio.model.PhotographerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface PhotographerScheduleRepository extends JpaRepository<PhotographerSchedule, Long> {
    @Query("SELECT s FROM PhotographerSchedule s WHERE s.employee.id = :employeeId AND s.date = :date AND " +
            "((s.startTime < :endTime AND s.endTime > :startTime))")
    List<PhotographerSchedule> findConflicts(
            @Param("employeeId") Long employeeId,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );
}