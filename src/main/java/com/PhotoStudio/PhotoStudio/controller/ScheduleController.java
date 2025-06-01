package com.PhotoStudio.PhotoStudio.controller;

import com.PhotoStudio.PhotoStudio.model.PhotoSession;
import com.PhotoStudio.PhotoStudio.model.PhotographerSchedule;
import com.PhotoStudio.PhotoStudio.service.PhotoSessionService;
import com.PhotoStudio.PhotoStudio.service.PhotographerScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final PhotoSessionService photoSessionService;
    private final PhotographerScheduleService scheduleService;

    @Autowired
    public ScheduleController(PhotoSessionService photoSessionService,
                              PhotographerScheduleService scheduleService) {
        this.photoSessionService = photoSessionService;
        this.scheduleService = scheduleService;
    }

    @GetMapping("/events")
    public List<CalendarEvent> getEvents(@RequestParam(required = false) Long employeeId) {
        List<CalendarEvent> events = new ArrayList<>();

        // Обработка фотосессий
        List<PhotoSession> sessions = (employeeId != null)
                ? photoSessionService.findByPhotographerId(employeeId)
                : photoSessionService.findAll();

        events.addAll(sessions.stream()
                .map(this::convertToEvent)
                .collect(Collectors.toList()));

        // Обработка расписаний
        List<PhotographerSchedule> schedules = (employeeId != null)
                ? scheduleService.findByEmployeeId(employeeId)
                : scheduleService.findAll();

        events.addAll(schedules.stream()
                .map(this::convertScheduleToEvent)
                .collect(Collectors.toList()));

        return events;
    }

    private CalendarEvent convertToEvent(PhotoSession session) {
        CalendarEvent event = new CalendarEvent();
        event.setId(session.getId());
        event.setTitle(session.getClient().getFullName() + " - " + session.getServiceType().getName());
        event.setStart(session.getStartTime());
        event.setEnd(session.getStartTime().plusHours(session.getServiceType().getDurationHours()));
        event.setColor(session.getStatus().equals("completed") ? "green" :
                session.getStatus().equals("canceled") ? "red" : "blue");
        event.setType("session");

        // Добавляем дополнительные данные для отображения в модальном окне
        event.setClient(session.getClient().getFullName());
        event.setServiceType(session.getServiceType().getName());
        event.setPhotographer(session.getPhotographer() != null ?
                session.getPhotographer().getFullName() : "Не назначен");
        event.setStatus(session.getStatus());
        event.setNotes(session.getNotes());
        event.setStartTimeFormatted(session.getStartTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        event.setEndTimeFormatted(session.getStartTime().plusHours(session.getServiceType().getDurationHours())
                .format(DateTimeFormatter.ofPattern("HH:mm")));

        return event;
    }

    private CalendarEvent convertScheduleToEvent(PhotographerSchedule schedule) {
        CalendarEvent event = new CalendarEvent();
        event.setId(schedule.getId());
        event.setTitle("Рабочий график: " + schedule.getEmployee().getFullName());
        event.setStart(LocalDateTime.of(schedule.getDate(), schedule.getStartTime()));
        event.setEnd(LocalDateTime.of(schedule.getDate(), schedule.getEndTime()));
        event.setColor("#6c757d"); // Серый цвет
        event.setType("schedule");

        // Добавляем дополнительные данные для отображения в модальном окне
        event.setEmployee(schedule.getEmployee().getFullName());
        event.setDateFormatted(schedule.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        event.setStartTimeFormatted(schedule.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        event.setEndTimeFormatted(schedule.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")));

        return event;
    }

    static class CalendarEvent {
        private Long id;
        private String title;
        private LocalDateTime start;
        private LocalDateTime end;
        private String color;
        private String type;

        // Дополнительные поля для фотосессий
        private String client;
        private String serviceType;
        private String photographer;
        private String status;
        private String notes;
        private String startTimeFormatted;
        private String endTimeFormatted;

        // Дополнительные поля для расписания
        private String employee;
        private String dateFormatted;

        // Геттеры и сеттеры для всех полей
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public LocalDateTime getStart() { return start; }
        public void setStart(LocalDateTime start) { this.start = start; }

        public LocalDateTime getEnd() { return end; }
        public void setEnd(LocalDateTime end) { this.end = end; }

        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public String getClient() { return client; }
        public void setClient(String client) { this.client = client; }

        public String getServiceType() { return serviceType; }
        public void setServiceType(String serviceType) { this.serviceType = serviceType; }

        public String getPhotographer() { return photographer; }
        public void setPhotographer(String photographer) { this.photographer = photographer; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }

        public String getStartTimeFormatted() { return startTimeFormatted; }
        public void setStartTimeFormatted(String startTimeFormatted) { this.startTimeFormatted = startTimeFormatted; }

        public String getEndTimeFormatted() { return endTimeFormatted; }
        public void setEndTimeFormatted(String endTimeFormatted) { this.endTimeFormatted = endTimeFormatted; }

        public String getEmployee() { return employee; }
        public void setEmployee(String employee) { this.employee = employee; }

        public String getDateFormatted() { return dateFormatted; }
        public void setDateFormatted(String dateFormatted) { this.dateFormatted = dateFormatted; }
    }
}