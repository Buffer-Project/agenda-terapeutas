package org.buffer.agendaterapeutas.vo;

import org.buffer.agendaterapeutas.model.Session;
import org.buffer.agendaterapeutas.enums.SessionStatusEnum;

public class SessionVO {

    private Long idSession;
    private TherapistVO therapist;
    private PatientVO patient;
    private String startDateTime;
    private String endDateTime;
    private SessionStatusEnum status;


}
