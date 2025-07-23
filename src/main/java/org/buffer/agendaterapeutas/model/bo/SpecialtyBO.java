package org.buffer.agendaterapeutas.model.bo;

import org.buffer.agendaterapeutas.model.entity.Specialty;
import org.buffer.agendaterapeutas.model.vo.SpecialtyVO;

public class SpecialtyBO {

    private Long id;

    private String name;


    public SpecialtyBO() {}

    public SpecialtyBO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SpecialtyBO(Specialty specialty) {
        this.id = specialty.getId();
        this.name = specialty.getName();
    }

    public SpecialtyBO(SpecialtyVO specialtyVO) {
        this.id = specialtyVO.getId();
        this.name = specialtyVO.getName();
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
