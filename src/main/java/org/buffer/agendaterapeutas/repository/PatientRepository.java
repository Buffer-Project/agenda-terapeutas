package org.buffer.agendaterapeutas.repository;

import org.buffer.agendaterapeutas.model.Patient;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository {
    boolean existsById(long id);
}
