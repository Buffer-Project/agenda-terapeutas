package org.buffer.agendaterapeutas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity(name = "patient")
@PrimaryKeyJoinColumn(name = "user_id")
public class Patient extends User {

    private String healthInsurance;


    public Patient() {}


    public String getHealthInsurance() { return healthInsurance; }

    public void setHealthInsurance(String healthInsurance) {
        this.healthInsurance = healthInsurance;
    }
}
