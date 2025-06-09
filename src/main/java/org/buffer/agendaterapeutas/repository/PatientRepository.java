package org.buffer.agendaterapeutas.repository;

import org.buffer.agendaterapeutas.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    boolean existsById(long id);
    List<Patient> findByUserActiveTrue();
}
