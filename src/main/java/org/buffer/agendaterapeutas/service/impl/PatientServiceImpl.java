package org.buffer.agendaterapeutas.service.impl;

import org.buffer.agendaterapeutas.exception.PatientException;
import org.buffer.agendaterapeutas.exception.UserException;
import org.buffer.agendaterapeutas.exception.errors.PatientError;
import org.buffer.agendaterapeutas.exception.errors.UserError;
import org.buffer.agendaterapeutas.model.entity.Patient;
import org.buffer.agendaterapeutas.model.vo.PatientVO;
import org.buffer.agendaterapeutas.repository.IPatientRepository;
import org.buffer.agendaterapeutas.service.IPatientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements IPatientService {

    private final IPatientRepository patientRepository;

    public PatientServiceImpl(IPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientVO createPatient(PatientVO patientVO) {

        if (patientVO.getId() != null) {
            throw new PatientException(PatientError.INVALID_FORMAT);
        }
        if(patientRepository.existsByUserEmail(patientVO.getUser().getEmail())){
            throw new UserException(UserError.EMAIL_ALREADY_EXISTS);
        }
        Patient patient = patientRepository.save(new Patient(patientVO));
        return new PatientVO(patient);
    }

    @Override
    public PatientVO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientException(PatientError.NOT_FOUND, id));
        return new PatientVO(patient);
    }

    @Override
    public PatientVO updatePatient(PatientVO patient, Long id) {
        if (id == null || patient == null || patient.getId() == null) {
            throw new PatientException(PatientError.MISSING_ID);
        }
        if (!patient.getId().equals(id)) {
            throw new PatientException(PatientError.ID_CONFLICT);
        }
        if (!patientRepository.existsById(patient.getId())) {
            throw new PatientException(PatientError.NOT_FOUND, id);
        }

        Patient updatedPatient = patientRepository.save(new Patient(patient));
        return new PatientVO(updatedPatient);
    }

    public void deletePatientById(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientException(PatientError.NOT_FOUND, id));

        patient.getUser().setActive(false);
        patientRepository.save(patient);
    }

    public List<PatientVO> getAllActivePatients() {
        return patientRepository.findByUserActiveTrue()
                .stream()
                .map(PatientVO::new)
                .toList();
    }
}
