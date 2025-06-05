package org.buffer.agendaterapeutas.repository;

import org.buffer.agendaterapeutas.model.Therapist;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface TherapistRepository extends JpaRepository<Therapist, Long> {
    List<Therapist> findByDeletedFalse();
}
