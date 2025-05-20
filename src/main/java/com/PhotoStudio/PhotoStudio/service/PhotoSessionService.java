package com.PhotoStudio.PhotoStudio.service;

import com.PhotoStudio.PhotoStudio.model.PhotoSession;
import com.PhotoStudio.PhotoStudio.repository.PhotoSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PhotoSessionService {
    private final PhotoSessionRepository photoSessionRepository;

    @Autowired
    public PhotoSessionService(PhotoSessionRepository photoSessionRepository) {
        this.photoSessionRepository = photoSessionRepository;
    }

    public List<PhotoSession> findAll() {
        return photoSessionRepository.findAllWithDetails();
    }

    public PhotoSession findById(Long id) {
        return photoSessionRepository.findById(id).orElse(null);
    }

    public PhotoSession save(PhotoSession photoSession) {
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