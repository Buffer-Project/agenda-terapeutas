package org.buffer.agendaterapeutas.model.vo;

import org.buffer.agendaterapeutas.model.bo.SpecialtyBO;

public class SpecialtyVO {

    private Long id;

    private String name;

    public SpecialtyVO() {}

    public SpecialtyVO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SpecialtyVO(SpecialtyBO specialtyBO) {
        this.id = specialtyBO.getId();
        this.name = specialtyBO.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
