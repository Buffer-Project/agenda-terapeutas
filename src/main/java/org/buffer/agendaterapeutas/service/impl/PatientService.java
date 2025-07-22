package org.buffer.agendaterapeutas.service.impl;

import org.buffer.agendaterapeutas.model.Patient;
import org.buffer.agendaterapeutas.repository.PatientRepository;
import org.buffer.agendaterapeutas.service.IPatientService;
import org.buffer.agendaterapeutas.vo.PatientVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService implements IPatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }


    public Patient createPatient(PatientVO patientVO) throws Exception {

        if (patientRepository.existsByUserEmail(patientVO.getEmail())) {
            throw new Exception("E-mail already registered");
        }
        return patientRepository.save(new Patient(patientVO));
    }

    public Patient getPatientById(Long id) throws Exception {
        return patientRepository.findById(id)
                .orElseThrow(() -> new Exception("Patient not found with id: " + id));
    }

    public Patient updatePatient(Patient patient) throws Exception {
        Long id = patient.getId();
        if (id == null || !patientRepository.existsById(id)) {
            throw new Exception("Patient not found with id: " + id);
        }
        return patientRepository.save(patient);
    }

    public void deletePatientById(Long id) throws Exception {
        if (!patientRepository.existsById(id)) {
            throw new Exception("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findByUserActiveTrue();
    }
}
