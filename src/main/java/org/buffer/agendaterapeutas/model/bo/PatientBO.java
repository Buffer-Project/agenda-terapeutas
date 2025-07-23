package org.buffer.agendaterapeutas.model.bo;

import org.buffer.agendaterapeutas.model.entity.Patient;
import org.buffer.agendaterapeutas.model.entity.User;
import org.buffer.agendaterapeutas.model.vo.PatientVO;

public class PatientBO {

     private Long id;

    private UserBO user;

    private String healthInsurance;

    public PatientBO() {
    }

    public PatientBO(Long id, UserBO user, String healthInsurance) {
        this.id = id;
        this.user = user;
        this.healthInsurance = healthInsurance;
    }

    public PatientBO(Patient patient) {
        this.id = patient.getId();
        this.user = patient.getUser()!=null?new UserBO(patient.getUser()):null;
        this.healthInsurance = patient.getHealthInsurance();
    }

    public PatientBO(PatientVO patientVO) {
        this.id = patientVO.getId();
        this.user = patientVO.getUser()!=null?new UserBO(patientVO.getUser()):null;
        this.healthInsurance = patientVO.getHealthInsurance();
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

    public String getHealthInsurance() {
        return healthInsurance;
    }

    public void setHealthInsurance(String healthInsurance) {
        this.healthInsurance = healthInsurance;
    }
}
