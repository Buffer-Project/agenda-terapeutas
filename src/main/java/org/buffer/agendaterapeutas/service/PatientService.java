package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.model.Patient;
import org.buffer.agendaterapeutas.repository.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient createPatient(Patient patient) throws Exception{
        if(patientRepository.existsById(patient.getId())) {
            throw new Exception("Patient with id " + patient.getId() + " already exists");
        }
        return patientRepository.save(patient);
    }

    @Override
    public Patient getPatientById(Long id) throws Exception{
        return patientRepository.findById(id).orElseThrow();
    }

}
