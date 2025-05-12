package com.PhotoStudio.PhotoStudio.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "session_employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionEmployee {
    @EmbeddedId
    private SessionEmployeeId id;

    @ManyToOne
    @MapsId("sessionId")
    @JoinColumn(name = "session_id")
    private PhotoSession session;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(nullable = false)
    private String role;
}

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
class SessionEmployeeId implements Serializable {
    private Long sessionId;
    private Long employeeId;
}