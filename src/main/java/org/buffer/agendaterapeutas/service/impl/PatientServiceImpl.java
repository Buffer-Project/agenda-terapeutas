package org.buffer.agendaterapeutas.service.impl;

import org.buffer.agendaterapeutas.model.bo.PatientBO;
import org.buffer.agendaterapeutas.exception.PatientNotFoundException;
import org.buffer.agendaterapeutas.model.entity.Patient;
import org.buffer.agendaterapeutas.repository.IPatientRepository;
import org.buffer.agendaterapeutas.service.IPatientService;
import org.buffer.agendaterapeutas.model.vo.PatientVO;
import org.springframework.stereotype.Service;
import org.buffer.agendaterapeutas.exception.EmailAlreadyTakenException;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements IPatientService {

    private final IPatientRepository patientRepository;

    public PatientServiceImpl(IPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientVO createPatient(PatientVO patientVO) {

        if (patientRepository.existsByUserEmail(patientVO.getUser().getEmail())) {
            throw new EmailAlreadyTakenException();
        }
        Patient patient = patientRepository.save(new Patient(new PatientBO(patientVO)));
        return new PatientVO(new PatientBO(patient));
    }

    @Override
    public PatientVO getPatientById(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            throw new PatientNotFoundException();
        }
        PatientBO patientBO = new PatientBO(patient.get());
        return new PatientVO(patientBO);
    }

    @Override
    public PatientVO updatePatient(PatientVO patient, Long id) {
        if (id == null) {
            throw new PatientNotFoundException("Id cannot be null");//TODO: mejorar mensaje y excepcion
        }
        if (!patient.getId().equals(id)) {
            throw new PatientNotFoundException("Id does not match"); //TODO: mejorar mensaje y excepcion
        }
        if (patient.getId() == null || !patientRepository.existsById(patient.getId())) {
            throw new PatientNotFoundException(); //TODO: mejorar mensaje y excepcion
        }
        PatientBO patientBO = new PatientBO(patient);
        Patient updatedPatient = patientRepository.save(new Patient(patientBO));
        return new PatientVO(new PatientBO(updatedPatient));

    }

    public void deletePatientById(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            throw new PatientNotFoundException();
        }
        patientRepository.delete(patient.get());
    }

    public List<PatientVO> getAllPatients() {
        return patientRepository.findByUserActiveTrue()
                .stream()
                .map(p -> new PatientVO(new PatientBO(p)))
                .toList();
    }
}
