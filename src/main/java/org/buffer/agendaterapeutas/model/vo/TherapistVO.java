package org.buffer.agendaterapeutas.model.vo;


import org.buffer.agendaterapeutas.model.bo.TherapistBO;

import org.buffer.agendaterapeutas.model.entity.Therapist;


import java.util.List;

public class TherapistVO {

    private Long id;

    private UserVO user;

    private String specialty;

    private List<SessionVO> sessions;

    public TherapistVO(){}

    public TherapistVO(Long id, UserVO user, String specialty, List<SessionVO> sessions) {
        this.id = id;
        this.user = user;
        this.specialty = specialty;
        this.sessions = sessions;
    }

    public TherapistVO(TherapistBO therapistBO) {
        this.id = therapistBO.getId();
        this.user = therapistBO.getUser()!= null ? new UserVO(therapistBO.getUser()) : null;
        this.specialty = therapistBO.getSpecialty();
    }

    public TherapistVO(Therapist therapist) {
        this.id = therapist.getId();
        this.user = therapist.getUser() != null ? new UserVO(therapist.getUser()) : null;
        this.specialty = therapist.getSpecialty();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public List<SessionVO> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionVO> sessions) {
        this.sessions = sessions;
    }
}
