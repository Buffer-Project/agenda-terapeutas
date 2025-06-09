package org.buffer.agendaterapeutas.vo;

import org.buffer.agendaterapeutas.model.Session;
import org.buffer.agendaterapeutas.model.Therapist;

import java.util.ArrayList;
import java.util.List;

public class TherapistVO {
    private final Long id;
    private final String username;
    private final String name;
    private final String specialtyName;
    private final List<Long> sessionIds;

    public TherapistVO(Therapist therapist) {
        this.id = therapist.getId();
        this.name = therapist.getFirstName() + " " + therapist.getLastName();
        this.username = therapist.getUsername();

        if (therapist.getSpecialty() != null) {
            this.specialtyName = therapist.getSpecialty().getName();
        } else {
            this.specialtyName = null;
        }

        this.sessionIds = new ArrayList<>();
        if (therapist.getSessions() != null) {
            for (Session session : therapist.getSessions()) {
                Long patientId = session.getPatientId();
                if (patientId != null) {
                    this.sessionIds.add(patientId);
                }
            }
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public List<Long> getSessionIds() {
        return sessionIds;
    }
}
