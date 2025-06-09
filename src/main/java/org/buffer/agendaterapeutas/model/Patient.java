package org.buffer.agendaterapeutas.model;

import jakarta.persistence.*;

@Entity(name = "patient")
public class Patient {
    @Id
    private Long id;

    @OneToOne
    private User user;

    private String healthInsurance;


    public Patient() {}


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
