package org.buffer.agendaterapeutas.model.bo;

import org.buffer.agendaterapeutas.enums.SessionStatusEnum;
import org.buffer.agendaterapeutas.model.entity.Session;
import org.buffer.agendaterapeutas.model.vo.SessionVO;
import java.time.LocalDateTime;

public class SessionBO {

    private Long id;
    private TherapistBO therapist;
    private PatientBO patient;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private SessionStatusEnum status;

    public SessionBO() {
    }

    public SessionBO(Long id, TherapistBO therapist, PatientBO patient, LocalDateTime startDateTime, LocalDateTime endDateTime, SessionStatusEnum status) {
        this.id = id;
        this.therapist = therapist;
        this.patient = patient;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = status;
    }

    public SessionBO(Session session) {
        if (session != null) {
            this.id = session.getId();
            this.therapist = session.getTherapist() != null ? new TherapistBO(session.getTherapist()) : null;
            this.patient = session.getPatient() != null ? new PatientBO(session.getPatient()) : null;
            this.startDateTime = session.getStartDateTime();
            this.endDateTime = session.getEndDateTime();
            this.status = session.getStatus();
        }
    }

    public SessionBO(SessionVO sessionVO) {
        if (sessionVO != null) {
            this.id = sessionVO.getId();
            this.therapist = sessionVO.getTherapist() != null ? new TherapistBO(sessionVO.getTherapist()) : null;
            this.patient = sessionVO.getPatient() != null ? new PatientBO(sessionVO.getPatient()) : null;
            this.startDateTime = sessionVO.getStartDateTime();
            this.endDateTime = sessionVO.getEndDateTime();
            this.status = sessionVO.getStatus();
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TherapistBO getTherapist() {
        return therapist;
    }

    public void setTherapist(TherapistBO therapist) {
        this.therapist = therapist;
    }

    public PatientBO getPatient() {
        return patient;
    }

    public void setPatient(PatientBO patient) {
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