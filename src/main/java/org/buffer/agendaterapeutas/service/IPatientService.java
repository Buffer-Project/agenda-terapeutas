package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.model.vo.PatientVO;

import java.util.List;

public interface IPatientService {
    PatientVO createPatient(PatientVO patientVO);
    PatientVO getPatientById(Long id);
    PatientVO updatePatient(PatientVO patient,Long id);
    void deletePatientById(Long id);
    List<PatientVO> getAllActivePatients();
}
