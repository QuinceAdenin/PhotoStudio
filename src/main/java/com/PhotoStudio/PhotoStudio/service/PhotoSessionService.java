package com.PhotoStudio.PhotoStudio.service;

import com.PhotoStudio.PhotoStudio.model.PhotoSession;
import com.PhotoStudio.PhotoStudio.repository.PhotoSessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class PhotoSessionService {
    private final PhotoSessionRepository photoSessionRepository;
    private final PhotographerScheduleService scheduleService;

    @Autowired
    public PhotoSessionService(PhotoSessionRepository photoSessionRepository, PhotographerScheduleService scheduleService) {
        this.photoSessionRepository = photoSessionRepository;
        this.scheduleService = scheduleService;
    }

    public List<PhotoSession> findAll() {
        return photoSessionRepository.findAllOrderedById();
    }

    public PhotoSession findById(Long id) {
        return photoSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Фотосессия не найдена"));
    }

    public PhotoSession save(PhotoSession photoSession) {
        // Временная переменная для проверки доступности
        PhotoSession tempSession = new PhotoSession();
        BeanUtils.copyProperties(photoSession, tempSession);

        if (tempSession.getId() == null) {
            Long maxId = photoSessionRepository.findMaxId();
            tempSession.setId(maxId != null ? maxId + 1 : 1);
        }

        if (tempSession.getPhotographer() != null) {
            LocalDateTime startTime = tempSession.getStartTime();
            LocalDateTime endTime = startTime.plusHours(tempSession.getServiceType().getDurationHours());

            List<PhotoSession> conflicts = photoSessionRepository.findConflictingSessions(
                    tempSession.getPhotographer().getId(),
                    startTime,
                    endTime,
                    tempSession.getId()
            );

            if (!conflicts.isEmpty()) {
                throw new RuntimeException("Фотограф занят в указанное время");
            }
        }

        // Копируем свойства обратно только после успешной проверки
        if (photoSession.getId() == null) {
            photoSession.setId(tempSession.getId());
        }

        return photoSessionRepository.save(photoSession);
    }


        public void deleteById(Long id) {
        photoSessionRepository.deleteById(id);
    }

    public List<PhotoSession> findByPeriod(LocalDateTime start, LocalDateTime end) {
        return photoSessionRepository.findByStartTimeBetween(start, end);
    }

    public List<PhotoSession> findByStatus(String status) {
        return photoSessionRepository.findByStatus(status);
    }

}