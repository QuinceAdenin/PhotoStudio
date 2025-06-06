package com.PhotoStudio.PhotoStudio.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalTime;

@Entity
@Table(name = "service_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceType {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

//    @Column(name = "duration")
//    private Integer durationMinutes;

    @Column(name = "duration", nullable = false)
    private Integer durationHours; // Добавлено поле для часов

    public Integer getDurationHours() {
        return durationHours;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDurationHours(Integer durationHours) {
        this.durationHours = durationHours;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}