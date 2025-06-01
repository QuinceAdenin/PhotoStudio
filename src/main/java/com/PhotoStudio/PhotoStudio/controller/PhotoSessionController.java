package com.PhotoStudio.PhotoStudio.controller;

import com.PhotoStudio.PhotoStudio.model.Employee;
import com.PhotoStudio.PhotoStudio.model.PhotoSession;
import com.PhotoStudio.PhotoStudio.model.ServiceType;
import com.PhotoStudio.PhotoStudio.repository.ClientRepository;
import com.PhotoStudio.PhotoStudio.repository.EmployeeRepository;
import com.PhotoStudio.PhotoStudio.repository.PhotoSessionRepository;
import com.PhotoStudio.PhotoStudio.repository.ServiceTypeRepository;
import com.PhotoStudio.PhotoStudio.service.PhotoSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/sessions")
public class PhotoSessionController {
    private final PhotoSessionService photoSessionService;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final PhotoSessionRepository photoSessionRepository;

    @Autowired
    public PhotoSessionController(PhotoSessionService photoSessionService, ClientRepository clientRepository, EmployeeRepository employeeRepository, ServiceTypeRepository serviceTypeRepository, PhotoSessionRepository photoSessionRepository) {
        this.photoSessionService = photoSessionService;
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.serviceTypeRepository = serviceTypeRepository;
        this.photoSessionRepository = photoSessionRepository;
    }

    @ModelAttribute
    public void addCommonAttributes(Model model) {
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("serviceTypes", serviceTypeRepository.findAll());
    }

    @GetMapping
    public String getAllSessions(Model model) {
        List<PhotoSession> sessions = photoSessionService.findAll();
        model.addAttribute("photoSessions", sessions);
        return "sessions/list";
    }

    @GetMapping("/{id}")
    public String viewSession(@PathVariable Long id, Model model) {
        PhotoSession session = photoSessionService.findById(id);
        model.addAttribute("photoSession", session);
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("serviceTypes", serviceTypeRepository.findAll());
        return "sessions/view";
    }


    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("photoSession", new PhotoSession());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("serviceTypes", serviceTypeRepository.findAll());
        return "sessions/form";
    }

    @PostMapping
    public String createSession(
            @ModelAttribute("photoSession") PhotoSession photoSession,
            Model model
    ) {
        try {
            // Убедимся, что ID не установлен для новых сессий
            if (photoSession.getId() != null) {
                photoSession.setId(null);
            }

            photoSessionService.save(photoSession);
            return "redirect:/sessions";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("photoSession", photoSession);
            model.addAttribute("clients", clientRepository.findAll());
            model.addAttribute("employees", employeeRepository.findAll());
            model.addAttribute("serviceTypes", serviceTypeRepository.findAll());
            return "sessions/form";
        }
    }

//    @GetMapping("/search")
//    public String searchSessions(Model model) {
//        model.addAttribute("photoSessions", photoSessionService.findAll());
//        model.addAttribute("clients", clientRepository.findAll());
//        model.addAttribute("employees", employeeRepository.findAll());
//        model.addAttribute("serviceTypes", serviceTypeRepository.findAll());
//        return "sessions/list";
//    }
// Форма редактирования
@GetMapping("/{id}/edit")
public String showEditForm(@PathVariable Long id, Model model) {
    PhotoSession session = photoSessionService.findById(id);
    model.addAttribute("photoSession", session);
    return "sessions/form";
}

    @PostMapping("/{id}")
    public String updateSession(
            @PathVariable Long id,
            @ModelAttribute("photoSession") PhotoSession photoSession,
            Model model
    ) {
        try {
            PhotoSession existingSession = photoSessionService.findById(id);

            // Сохраняем оригинальные значения для проверки изменений
            Employee originalPhotographer = existingSession.getPhotographer();
            LocalDateTime originalStartTime = existingSession.getStartTime();
            ServiceType originalServiceType = existingSession.getServiceType();

            // Обновляем поля
            existingSession.setClient(photoSession.getClient());
            existingSession.setPhotographer(photoSession.getPhotographer());
            existingSession.setServiceType(photoSession.getServiceType());
            existingSession.setStatus(photoSession.getStatus());
            existingSession.setNotes(photoSession.getNotes());
            existingSession.setStartTime(photoSession.getStartTime());

            // Проверяем, изменились ли критические параметры
            boolean needsValidation =
                    !Objects.equals(originalPhotographer, photoSession.getPhotographer()) ||
                            !Objects.equals(originalStartTime, photoSession.getStartTime()) ||
                            !Objects.equals(originalServiceType, photoSession.getServiceType());

            if (needsValidation) {
                photoSessionService.save(existingSession);
            } else {
                photoSessionRepository.save(existingSession);
            }

            return "redirect:/sessions";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("photoSession", photoSession);
            model.addAttribute("clients", clientRepository.findAll());
            model.addAttribute("employees", employeeRepository.findAll());
            model.addAttribute("serviceTypes", serviceTypeRepository.findAll());
            return "sessions/form";
        }
    }
    // Удаление фотосессии
    @GetMapping("/{id}/delete")
    public String deleteSession(@PathVariable Long id) {
        photoSessionService.deleteById(id);
        return "redirect:/sessions";
    }
}