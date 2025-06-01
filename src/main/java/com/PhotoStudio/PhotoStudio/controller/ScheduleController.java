// ScheduleController.java
package com.PhotoStudio.PhotoStudio.controller;

import com.PhotoStudio.PhotoStudio.model.PhotoSession;
import com.PhotoStudio.PhotoStudio.service.PhotoSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final PhotoSessionService photoSessionService;

    @Autowired
    public ScheduleController(PhotoSessionService photoSessionService) {
        this.photoSessionService = photoSessionService;
    }

    @GetMapping("/events")
    public List<CalendarEvent> getEvents() {
        return photoSessionService.findAll().stream()
                .map(this::convertToEvent)
                .collect(Collectors.toList());
    }

    private CalendarEvent convertToEvent(PhotoSession session) {
        CalendarEvent event = new CalendarEvent();
        event.setId(session.getId());
        event.setTitle(session.getClient().getFullName() + " - " + session.getServiceType().getName());
        event.setStart(session.getStartTime());
        event.setEnd(session.getStartTime().plusHours(session.getServiceType().getDurationHours()));
        event.setColor(session.getStatus().equals("completed") ? "green" :
                session.getStatus().equals("canceled") ? "red" : "blue");
        return event;
    }

    static class CalendarEvent {
        private Long id;
        private String title;
        private LocalDateTime start;
        private LocalDateTime end;
        private String color;

        public Long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public LocalDateTime getStart() {
            return start;
        }

        public LocalDateTime getEnd() {
            return end;
        }

        public String getColor() {
            return color;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setStart(LocalDateTime start) {
            this.start = start;
        }

        public void setEnd(LocalDateTime end) {
            this.end = end;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}