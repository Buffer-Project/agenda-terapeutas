package org.buffer.agendaterapeutas.repository;

import org.buffer.agendaterapeutas.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Long> {
    boolean existsByUserEmail(String email);
    List<Patient> findByUserActiveTrue();
}
