// CalendarController.java
package com.PhotoStudio.PhotoStudio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;

@Controller
public class CalendarController {
    @GetMapping("/calendar")
    public String showCalendar(
            @RequestParam(required = false, defaultValue = "0") int year,
            @RequestParam(required = false, defaultValue = "0") int month,
            Model model
    ) {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();

        model.addAttribute("year", year > 0 ? year : currentYear);
        model.addAttribute("month", month > 0 ? month : currentMonth);
        return "calendar";
    }
}