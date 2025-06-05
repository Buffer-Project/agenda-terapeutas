package org.buffer.agendaterapeutas.vo;

import org.buffer.agendaterapeutas.model.Patient;

public class PatientVO {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String healthInsurance;


    public PatientVO(Patient patient) {
        this.id = patient.getId();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.email = patient.getEmail();
        this.healthInsurance = patient.getHealthInsurance();
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() { return lastName; }

    public String getEmail() {
        return email;
    }

    public String getHealthInsurance() { return healthInsurance; }
}
