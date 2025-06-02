package org.buffer.agendaterapeutas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity(name = "patient")
@PrimaryKeyJoinColumn(name = "user_id")
public class Patient extends User {

    private String patientName;
    private String email;
    private String phone;
    private String gender;
    private String birthDate;
    private String healthInsurance;

    public Patient() {}

    public String getPatientName() { return patientName; }

    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public String getBirthDate() { return birthDate; }

    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    public String getHealthInsurance() { return healthInsurance; }

    public void setHealthInsurance(String healthInsurance) {
        this.healthInsurance = healthInsurance;
    }
}
