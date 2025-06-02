package org.buffer.agendaterapeutas.vo;

import org.buffer.agendaterapeutas.model.Patient;

public class PatientVO {
    private Long id;
    private String patientName;
    private String name;
    private String email;

    public PatientVO(Patient patient) {
        this.id = patient.getId();
        this.patientName = patient.getPatientName();
        this.name = patient.getName();
        this.email = patient.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
