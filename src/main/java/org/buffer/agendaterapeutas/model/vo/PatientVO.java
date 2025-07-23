package org.buffer.agendaterapeutas.model.vo;

import org.buffer.agendaterapeutas.model.bo.PatientBO;
import org.buffer.agendaterapeutas.model.entity.User;

public class PatientVO {
    private Long id;

    private UserVO user;

    private String healthInsurance;

    public PatientVO(){}

    public PatientVO(Long id, UserVO user, String healthInsurance) {
        this.id = id;
        this.user = user;
        this.healthInsurance = healthInsurance;
    }

    public PatientVO(PatientBO patientBO) {
        this.id = patientBO.getId();
        this.user = patientBO.getUser()!=null?new UserVO(patientBO.getUser()):null;
        this.healthInsurance = patientBO.getHealthInsurance();
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

    public String getHealthInsurance() {
        return healthInsurance;
    }

    public void setHealthInsurance(String healthInsurance) {
        this.healthInsurance = healthInsurance;
    }
}
