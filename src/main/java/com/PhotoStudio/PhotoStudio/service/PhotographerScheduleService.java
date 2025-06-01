// PhotographerScheduleService.java
package com.PhotoStudio.PhotoStudio.service;

import com.PhotoStudio.PhotoStudio.model.PhotoSession;
import com.PhotoStudio.PhotoStudio.model.PhotographerSchedule;
import com.PhotoStudio.PhotoStudio.repository.PhotoSessionRepository;
import com.PhotoStudio.PhotoStudio.repository.PhotographerScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class PhotographerScheduleService {
    private final PhotographerScheduleRepository scheduleRepository;
    private final PhotoSessionRepository photoSessionRepository;

    @Autowired
    public PhotographerScheduleService(PhotographerScheduleRepository scheduleRepository, PhotoSessionRepository photoSessionRepository) {
        this.scheduleRepository = scheduleRepository;
        this.photoSessionRepository = photoSessionRepository;
    }

    public boolean isPhotographerAvailable(
            Long employeeId,
            LocalDate date,
            LocalTime start,
            LocalTime end,
            Long excludeSessionId
    ) {
        // 1. Проверяем конфликты в расписании фотографа
        List<PhotographerSchedule> scheduleConflicts = scheduleRepository.findConflicts(
                employeeId, date, start, end
        );

        // 2. Проверяем конфликты с другими фотосессиями
        LocalDateTime startDateTime = LocalDateTime.of(date, start);
        LocalDateTime endDateTime = LocalDateTime.of(date, end);

        List<PhotoSession> sessionConflicts = photoSessionRepository.findConflictingSessions(
                employeeId,
                startDateTime,
                endDateTime,
                excludeSessionId
        );

        return scheduleConflicts.isEmpty() && sessionConflicts.isEmpty();
    }

    public long countAll() {
        return scheduleRepository.count();
    }

    public List<PhotographerSchedule> findAll() {
        return scheduleRepository.findAll();
    }

    public PhotographerSchedule findById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    public PhotographerSchedule save(PhotographerSchedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public void deleteById(Long id) {
        scheduleRepository.deleteById(id);
    }

    public List<PhotographerSchedule> findByEmployeeId(Long employeeId) {
        return scheduleRepository.findByEmployeeId(employeeId);
    }

}