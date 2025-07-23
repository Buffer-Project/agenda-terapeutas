package org.buffer.agendaterapeutas.model.entity;

import jakarta.persistence.*;
import org.buffer.agendaterapeutas.model.bo.SessionBO;
import org.buffer.agendaterapeutas.enums.SessionStatusEnum;
import java.time.LocalDateTime;

@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSession;

    @ManyToOne
    @JoinColumn(name = "id_therapist")
    private Therapist therapist;

    @ManyToOne
    @JoinColumn(name = "id_patient")
    private Patient patient;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    private SessionStatusEnum status;

    public Session() {
    }

    public Session(Therapist therapist, Patient patient, LocalDateTime startDateTime, LocalDateTime endDateTime, SessionStatusEnum status) {
        this.therapist = therapist;
        this.patient = patient;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = status;
    }


    public Session(SessionBO sessionBO) {
        if (sessionBO != null) {
            this.idSession = sessionBO.getIdSession();
            this.therapist = sessionBO.getTherapist() != null ? new Therapist(sessionBO.getTherapist()) : null;
            this.patient = sessionBO.getPatient() != null ? new Patient(sessionBO.getPatient()) : null;
            this.startDateTime = sessionBO.getStartDateTime();
            this.endDateTime = sessionBO.getEndDateTime();
            this.status = sessionBO.getStatus();
        }
    }


    public Long getIdSession() {
        return idSession;
    }

    public void setIdSession(Long idSession) {
        this.idSession = idSession;
    }

    public Therapist getTherapist() {
        return therapist;
    }

    public void setTherapist(Therapist therapist) {
        this.therapist = therapist;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public SessionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(SessionStatusEnum status) {
        this.status = status;
    }
}