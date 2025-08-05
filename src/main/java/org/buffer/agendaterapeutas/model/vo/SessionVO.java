package org.buffer.agendaterapeutas.model.vo;

import org.buffer.agendaterapeutas.model.bo.SessionBO;
import org.buffer.agendaterapeutas.enums.SessionStatusEnum;
import org.buffer.agendaterapeutas.model.entity.Session;

import java.time.LocalDateTime;

public class SessionVO {

    private Long id;
    private TherapistVO therapist;
    private PatientVO patient;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private SessionStatusEnum status;

    public SessionVO() {
    }

    public SessionVO(Long id, TherapistVO therapist, PatientVO patient, LocalDateTime startDateTime, LocalDateTime endDateTime, SessionStatusEnum status) {
        this.id = id;
        this.therapist = therapist;
        this.patient = patient;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = status;
    }


    public SessionVO(SessionBO sessionBO) {
        if (sessionBO != null) {
            this.id = sessionBO.getId();
            this.therapist = sessionBO.getTherapist() != null ? new TherapistVO(sessionBO.getTherapist()) : null;
            this.patient = sessionBO.getPatient() != null ? new PatientVO(sessionBO.getPatient()) : null;
            this.startDateTime = sessionBO.getStartDateTime();
            this.endDateTime = sessionBO.getEndDateTime();
            this.status = sessionBO.getStatus();
        }
    }

    public SessionVO(Session session) {
        if (session != null) {
            this.id = session.getId();
            this.therapist = session.getTherapist() != null ? new TherapistVO(session.getTherapist()) : null;
            this.patient = session.getPatient() != null ? new PatientVO(session.getPatient()) : null;
            this.startDateTime = session.getStartDateTime();
            this.endDateTime = session.getEndDateTime();
            this.status = session.getStatus();
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TherapistVO getTherapist() {
        return therapist;
    }

    public void setTherapist(TherapistVO therapist) {
        this.therapist = therapist;
    }

    public PatientVO getPatient() {
        return patient;
    }

    public void setPatient(PatientVO patient) {
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