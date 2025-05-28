package com.PhotoStudio.PhotoStudio.controller;

import com.PhotoStudio.PhotoStudio.model.PhotoSession;
import com.PhotoStudio.PhotoStudio.repository.ClientRepository;
import com.PhotoStudio.PhotoStudio.repository.EmployeeRepository;
import com.PhotoStudio.PhotoStudio.repository.ServiceTypeRepository;
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
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceTypeRepository serviceTypeRepository;

    @Autowired
    public PhotoSessionController(PhotoSessionService photoSessionService, ClientRepository clientRepository, EmployeeRepository employeeRepository, ServiceTypeRepository serviceTypeRepository) {
        this.photoSessionService = photoSessionService;
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.serviceTypeRepository = serviceTypeRepository;
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
    public String createSession(@ModelAttribute("photoSession") PhotoSession photoSession) {
        photoSessionService.save(photoSession);
        return "redirect:/sessions"; // Перенаправление на список сессий после сохранения
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
            @ModelAttribute("photoSession") PhotoSession photoSession
    ) {
        // Устанавливаем ID из URL в объект
        photoSession.setId(id);

        // Обновляем только необходимые поля
        PhotoSession existingSession = photoSessionService.findById(id);
        existingSession.setClient(photoSession.getClient());
        existingSession.setPhotographer(photoSession.getPhotographer());
        existingSession.setServiceType(photoSession.getServiceType());
        existingSession.setStartTime(photoSession.getStartTime());
        existingSession.setStatus(photoSession.getStatus());
        existingSession.setNotes(photoSession.getNotes());

        photoSessionService.save(existingSession);
        return "redirect:/sessions";
    }
    // Удаление фотосессии
    @GetMapping("/{id}/delete")
    public String deleteSession(@PathVariable Long id) {
        photoSessionService.deleteById(id);
        return "redirect:/sessions";
    }
}