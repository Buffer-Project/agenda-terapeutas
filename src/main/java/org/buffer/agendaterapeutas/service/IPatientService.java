package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.model.Patient;

import java.util.List;

public interface IPatientService {
    Patient createPatient(Patient patient) throws Exception;
    Patient getPatientById(Long id) throws Exception;
    Patient updatePatient(Patient patient) throws Exception;
    void deletePatientById(Long id) throws Exception;
    List<Patient> getAllPatients();
}
