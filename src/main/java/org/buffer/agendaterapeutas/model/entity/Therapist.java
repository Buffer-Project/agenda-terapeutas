package org.buffer.agendaterapeutas.model.entity;

import jakarta.persistence.*;
import org.buffer.agendaterapeutas.model.bo.TherapistBO;
import org.buffer.agendaterapeutas.model.vo.TherapistVO;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity(name = "therapist")

public class Therapist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private User user;

    private String specialty;

    @OneToMany(mappedBy = "therapist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Session> sessions;

    public Therapist() {
    }

    public Therapist(Long id, User user, String specialty, List<Session> sessions) {
        this.id = id;
        this.user = user;
        this.specialty = specialty;
        this.sessions = sessions;
    }

    public Therapist(TherapistBO therapistBO) {
        this.id = therapistBO.getId();
        this.user = therapistBO.getUser() != null ? new User(therapistBO.getUser()) : null;
        this.specialty = therapistBO.getSpecialty();
        this.sessions = therapistBO.getSessions() != null ? therapistBO.getSessions().stream().map(Session::new).toList() : null;
    }

    public Therapist(TherapistVO therapistVO) {
        this.id = therapistVO.getId();
        this.user = therapistVO.getUser() != null ? new User(therapistVO.getUser()) : null;
        this.specialty = therapistVO.getSpecialty();
        this.sessions = therapistVO.getSessions() != null ? therapistVO.getSessions().stream().map(Session::new).toList() : null;
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

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}
