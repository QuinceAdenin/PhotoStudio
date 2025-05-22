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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

//    @Column(name = "duration")
//    private Integer durationMinutes;

    @Column(name = "duration", nullable = false)
    private Integer durationHours; // Добавлено поле для часов

    public void setId(Long id) {
        this.id = id;
    }
}