package org.buffer.agendaterapeutas.model.vo;


import org.buffer.agendaterapeutas.model.bo.TherapistBO;
import org.buffer.agendaterapeutas.model.entity.Session;
import org.buffer.agendaterapeutas.model.entity.Specialty;

import org.buffer.agendaterapeutas.model.entity.User;



import java.util.List;

public class TherapistVO {

    private Long id;

    private UserVO user;

    private SpecialtyVO specialty;

    private List<SessionVO> sessions;

    public TherapistVO(){}

    public TherapistVO(Long id, UserVO user, SpecialtyVO specialty, List<SessionVO> sessions) {
        this.id = id;
        this.user = user;
        this.specialty = specialty;
        this.sessions = sessions;
    }

    public TherapistVO(TherapistBO therapistBO) {
        this.id = therapistBO.getId();
        this.user = therapistBO.getUser()!= null ? new UserVO(therapistBO.getUser()) : null;
        this.specialty = therapistBO.getSpecialty()== null ? null : new SpecialtyVO(therapistBO.getSpecialty());
        this.sessions = therapistBO.getSessions()!= null ? therapistBO.getSessions().stream().map(SessionVO::new).toList() : null;
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

    public SpecialtyVO getSpecialty() {
        return specialty;
    }

    public void setSpecialty(SpecialtyVO specialty) {
        this.specialty = specialty;
    }

    public List<SessionVO> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionVO> sessions) {
        this.sessions = sessions;
    }
}
