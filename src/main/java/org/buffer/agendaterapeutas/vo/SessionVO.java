package org.buffer.agendaterapeutas.vo;

import org.buffer.agendaterapeutas.model.Session;
import org.buffer.agendaterapeutas.model.SessionStatus;

public class SessionVO {
    private Long therapistId;
    private Long patientId;
    private String startDateTime;
    private String endDateTime;
    private SessionStatus status;

    public SessionVO(Session sessionById) {
    }

    public Long getTherapistId() {
        return therapistId;
    }

    public void setTherapistId(Long therapistId) {
        this.therapistId = therapistId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }
}
