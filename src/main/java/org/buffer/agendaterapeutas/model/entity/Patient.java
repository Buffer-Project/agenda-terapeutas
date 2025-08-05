package org.buffer.agendaterapeutas.model.entity;

import jakarta.persistence.*;
import org.buffer.agendaterapeutas.model.bo.PatientBO;
import org.buffer.agendaterapeutas.model.vo.PatientVO;

@Entity(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    private String healthInsurance;


    public Patient() {}

    public Patient(Long id, User user, String healthInsurance) {
        this.id = id;
        this.user = user;
        this.healthInsurance = healthInsurance;
    }

    public Patient(PatientBO patientBO) {
        this.id = patientBO.getId();
        this.user = patientBO.getUser() != null ? new User(patientBO.getUser()) : null;
        this.healthInsurance = patientBO.getHealthInsurance();
    }

    public Patient(PatientVO patientVO) {
        this.id = patientVO.getId();
        this.user = patientVO.getUser() != null ? new User(patientVO.getUser()) : null;
        this.healthInsurance = patientVO.getHealthInsurance();
    }


    public String getHealthInsurance() { return healthInsurance; }

    public void setHealthInsurance(String healthInsurance) {
        this.healthInsurance = healthInsurance;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
