package org.buffer.agendaterapeutas.service.impl;

import org.buffer.agendaterapeutas.exception.PatientException;
import org.buffer.agendaterapeutas.exception.UserException;
import org.buffer.agendaterapeutas.exception.errors.PatientError;
import org.buffer.agendaterapeutas.exception.errors.UserError;
import org.buffer.agendaterapeutas.model.entity.Patient;
import org.buffer.agendaterapeutas.model.vo.PatientVO;
import org.buffer.agendaterapeutas.model.vo.UserVO;
import org.buffer.agendaterapeutas.repository.IPatientRepository;
import org.buffer.agendaterapeutas.service.IPatientService;
import org.buffer.agendaterapeutas.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements IPatientService {

    private final IPatientRepository patientRepository;
    private final IUserService userService;

    public PatientServiceImpl(IPatientRepository patientRepository, IUserService userService) {
        this.patientRepository = patientRepository;
        this.userService = userService;
    }

    @Override
    public PatientVO createPatient(PatientVO patientVO) {

        Long userId = patientVO.getUser().getId();

        if(userId == null) {
            userService.createUser(new UserVO());
        } else{
            userService.getUserById(userId);
        }

        patientVO.setId(null);
        Patient patient = patientRepository.save(new Patient(patientVO));
        return new PatientVO(patient);
    }

    @Override
    public PatientVO getPatientById(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            throw new PatientException(PatientError.NOT_FOUND, id);
        }
        return new PatientVO(patient.get());
    }

    @Override
    public PatientVO updatePatient(PatientVO patient, Long id) {
        if (id == null) {
            throw new PatientException(PatientError.MISSING_ID);
        }
        if (!patient.getId().equals(id)) {
            throw new PatientException(PatientError.ID_CONFLICT);
        }
        if (patient.getId() == null || !patientRepository.existsById(patient.getId())) {
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
