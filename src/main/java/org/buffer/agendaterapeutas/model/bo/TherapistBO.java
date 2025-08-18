package org.buffer.agendaterapeutas.model.bo;

import org.buffer.agendaterapeutas.model.entity.Therapist;
import org.buffer.agendaterapeutas.model.vo.TherapistVO;

import java.util.List;

public class TherapistBO {

    private Long id;

    private UserBO user;

    private String specialty;

    private List<SessionBO> sessions;

    public TherapistBO() {
    }

    public TherapistBO(Long id, UserBO user, String specialty, List<SessionBO> sessions) {
        this.id = id;
        this.user = user;
        this.specialty = specialty;
        this.sessions = sessions;
    }

    public TherapistBO(Therapist therapist) {
        this.id = therapist.getId();
        this.user = therapist.getUser() != null ? new UserBO(therapist.getUser()) : null;
        this.specialty = therapist.getSpecialty();
        this.sessions = therapist.getSessions() != null ? therapist.getSessions().stream().map(SessionBO::new).toList() : null;
    }

    public TherapistBO(TherapistVO therapistVO) {
        this.id = therapistVO.getId();
        this.user = therapistVO.getUser() != null ? new UserBO(therapistVO.getUser()) : null;
        this.specialty = therapistVO.getSpecialty();
        this.sessions = therapistVO.getSessions() != null ? therapistVO.getSessions().stream().map(SessionBO::new).toList() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserBO getUser() {
        return user;
    }

    public void setUser(UserBO user) {
        this.user = user;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public List<SessionBO> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionBO> sessions) {
        this.sessions = sessions;
    }
}
