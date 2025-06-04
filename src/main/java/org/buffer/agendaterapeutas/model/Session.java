package org.buffer.agendaterapeutas.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSession;

    @ManyToOne
    @JoinColumn(name = "id_terapeuta")
    private Therapist therapist;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Patient patient;

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;

    @Enumerated(EnumType.STRING)
    private SessionStatus estado;

    // Constructors
    public Session() {}

    // Getters y Setters
    public Long getIdSession() {
        return idSession;
    }

    public void setIdSession(Long idSesion) {
        this.idSession = idSesion;
    }

    public User getTherapist() {
        return therapist;
    }

    public void setTherapist(Therapist terapeuta) {
        this.therapist = terapeuta;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(Patient paciente) {
        this.patient = paciente;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public SessionStatus getEstado() {
        return estado;
    }

    public void setEstado(SessionStatus estado) {
        this.estado = estado;
    }

    public Long getPatientId() {
        return null;
    }
}
