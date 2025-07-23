package org.buffer.agendaterapeutas.repository;

import org.buffer.agendaterapeutas.model.entity.Therapist;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface ITherapistRepository extends JpaRepository<Therapist, Long> {
    List<Therapist> findByUserActiveTrue();
}
