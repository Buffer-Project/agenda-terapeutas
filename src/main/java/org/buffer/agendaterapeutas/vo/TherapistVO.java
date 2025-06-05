package org.buffer.agendaterapeutas.vo;

import org.buffer.agendaterapeutas.model.Session;
import org.buffer.agendaterapeutas.model.Therapist;

import java.util.ArrayList;
import java.util.List;

public class TherapistVO {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String specialtyName;
    private List<Long> sessionIds;

    public TherapistVO(Therapist therapist) {
        this.id = therapist.getId();
        this.firstName = therapist.getFirstName();
        this.lastName = therapist.getLastName();
        this.username = therapist.getUsername();

        if (therapist.getSpecialty() != null) {
            this.specialtyName = therapist.getSpecialty().getName();
        } else {
            this.specialtyName = "";
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() { return lastName; }

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
