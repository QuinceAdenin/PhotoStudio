package com.PhotoStudio.PhotoStudio.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "photo_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoSession {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee photographer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_type_id", nullable = false)
    private ServiceType serviceType;

    @Column(name = "start_time", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;

    @Column(nullable = false)
    private String status;

    private String notes;

    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public Employee getPhotographer() {
        return photographer;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setPhotographer(Employee photographer) {
        this.photographer = photographer;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }
}