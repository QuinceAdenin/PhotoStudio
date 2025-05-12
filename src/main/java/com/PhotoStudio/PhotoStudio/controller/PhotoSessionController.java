package com.PhotoStudio.PhotoStudio.controller;

import com.PhotoStudio.PhotoStudio.model.PhotoSession;
import com.PhotoStudio.PhotoStudio.service.PhotoSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/sessions")
public class PhotoSessionController {
    private final PhotoSessionService photoSessionService;

    @Autowired
    public PhotoSessionController(PhotoSessionService photoSessionService) {
        this.photoSessionService = photoSessionService;
    }

    @GetMapping
    public String getAllSessions(Model model) {
        List<PhotoSession> sessions = photoSessionService.findAll();
        model.addAttribute("sessions", sessions);
        return "sessions/list";
    }

    @GetMapping("/{id}")
    public String getSessionById(@PathVariable Long id, Model model) {
        PhotoSession session = photoSessionService.findById(id);
        model.addAttribute("session", session);
        return "sessions/view";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("session", new PhotoSession());
        return "sessions/form";
    }

    @PostMapping
    public String createSession(@ModelAttribute PhotoSession session) {
        photoSessionService.save(session);
        return "redirect:/sessions";
    }

    @GetMapping("/search")
    public String searchSessions(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end,
            Model model) {

        List<PhotoSession> sessions;
        if (status != null && !status.isEmpty()) {
            sessions = photoSessionService.findByStatus(status);
        } else if (start != null && end != null) {
            LocalDateTime startDate = LocalDateTime.parse(start);
            LocalDateTime endDate = LocalDateTime.parse(end);
            sessions = photoSessionService.findByPeriod(startDate, endDate);
        } else {
            sessions = photoSessionService.findAll();
        }

        model.addAttribute("sessions", sessions);
        return "sessions/list";
    }
}