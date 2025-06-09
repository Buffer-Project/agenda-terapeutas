package org.buffer.agendaterapeutas.vo;

import org.buffer.agendaterapeutas.model.Patient;

public class PatientVO {
    private final Long id;
    private final String name;
    private final String email;
    private final String healthInsurance;


    public PatientVO(Patient patient) {
        this.id = patient.getId();
        this.name = patient.getFirstName() + " " + patient.getLastName();
        this.email = patient.getEmail();
        this.healthInsurance = patient.getHealthInsurance();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getHealthInsurance() { return healthInsurance; }
}
