package org.buffer.agendaterapeutas.vo;

import org.buffer.agendaterapeutas.model.Session;
import org.buffer.agendaterapeutas.model.Therapist;

import java.util.ArrayList;
import java.util.List;

public class TherapistVO {
    private final Long id;
    private final String username;
    private final String fullName;
    private final String firstName;
    private final String lastName;
    private final String specialtyName;
    private final List<Long> sessionIds;

    public TherapistVO(Therapist therapist) {
        this.id = therapist.getId();
        this.firstName = therapist.getUser().getFirstName();
        this.lastName = therapist.getUser().getLastName();
        this.fullName = this.getFirstName() + " " + this.getLastName();
        this.username = therapist.getUser().getUsername();

        if (therapist.getSpecialty() != null) {
            this.specialtyName = therapist.getSpecialty().getName();
        } else {
            this.specialtyName = null;
        }

        this.sessionIds = new ArrayList<>();
        if (therapist.getSessions() != null) {
            for (Session session : therapist.getSessions()) {
                sessionIds.add(session.getIdSession());
            }
        }
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
