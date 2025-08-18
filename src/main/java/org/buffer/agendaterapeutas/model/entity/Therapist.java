package org.buffer.agendaterapeutas.model.entity;

import jakarta.persistence.*;
import org.buffer.agendaterapeutas.model.bo.TherapistBO;
import org.buffer.agendaterapeutas.model.vo.TherapistVO;
import org.hibernate.annotations.Cascade;


@Entity(name = "therapist")

public class Therapist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private User user;

    private String specialty;

    public Therapist() {
    }

    public Therapist(Long id, User user, String specialty) {
        this.id = id;
        this.user = user;
        this.specialty = specialty;
    }

    public Therapist(TherapistBO therapistBO) {
        this.id = therapistBO.getId();
        this.user = therapistBO.getUser() != null ? new User(therapistBO.getUser()) : null;
        this.specialty = therapistBO.getSpecialty();
    }

    public Therapist(TherapistVO therapistVO) {
        this.id = therapistVO.getId();
        this.user = therapistVO.getUser() != null ? new User(therapistVO.getUser()) : null;
        this.specialty = therapistVO.getSpecialty();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
