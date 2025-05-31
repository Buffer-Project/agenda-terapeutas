package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.model.Patient;

public interface IPatientService {
    Patient createPatient(Patient patient) throws Exception;
    Patient readPatient(Patient patient) throws Exception;
    Patient updatePatient(Patient patient) throws Exception;
    void deletePatient(Patient patient) throws Exception;
}
