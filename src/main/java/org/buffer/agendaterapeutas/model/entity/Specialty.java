package org.buffer.agendaterapeutas.model.entity;

import jakarta.persistence.*;
import org.buffer.agendaterapeutas.model.bo.SpecialtyBO;

@Entity
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Specialty() {}

    public Specialty(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Specialty(SpecialtyBO specialtyBO) {
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
