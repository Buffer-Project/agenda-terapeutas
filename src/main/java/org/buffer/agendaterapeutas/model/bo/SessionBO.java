package org.buffer.agendaterapeutas.model.bo;

import org.buffer.agendaterapeutas.enums.SessionStatusEnum;
import org.buffer.agendaterapeutas.model.entity.Session;
import org.buffer.agendaterapeutas.model.vo.SessionVO;
import java.time.LocalDateTime;

public class SessionBO {

    private Long idSession;
    private TherapistBO therapist;
    private PatientBO patient;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private SessionStatusEnum status;

    public SessionBO() {
    }

    public SessionBO(Long idSession, TherapistBO therapist, PatientBO patient, LocalDateTime startDateTime, LocalDateTime endDateTime, SessionStatusEnum status) {
        this.idSession = idSession;
        this.therapist = therapist;
        this.patient = patient;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = status;
    }

    public SessionBO(Session session) {
        if (session != null) {
            this.idSession = session.getIdSession();
            this.therapist = session.getTherapist() != null ? new TherapistBO(session.getTherapist()) : null;
            this.patient = session.getPatient() != null ? new PatientBO(session.getPatient()) : null;
            this.startDateTime = session.getStartDateTime();
            this.endDateTime = session.getEndDateTime();
            this.status = session.getStatus();
        }
    }

    public SessionBO(SessionVO sessionVO) {
        if (sessionVO != null) {
            this.idSession = sessionVO.getIdSession();
            this.therapist = sessionVO.getTherapist() != null ? new TherapistBO(sessionVO.getTherapist()) : null;
            this.patient = sessionVO.getPatient() != null ? new PatientBO(sessionVO.getPatient()) : null;
            this.startDateTime = sessionVO.getStartDateTime();
            this.endDateTime = sessionVO.getEndDateTime();
            this.status = sessionVO.getStatus();
        }
    }


    public Long getIdSession() {
        return idSession;
    }

    public void setIdSession(Long idSession) {
        this.idSession = idSession;
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