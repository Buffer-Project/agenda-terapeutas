package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.exception.PatientException;
import org.buffer.agendaterapeutas.exception.SchedulerException;
import org.buffer.agendaterapeutas.exception.UserException;
import org.buffer.agendaterapeutas.model.bo.PatientBO;
import org.buffer.agendaterapeutas.model.entity.Patient;
import org.buffer.agendaterapeutas.model.vo.PatientVO;
import org.buffer.agendaterapeutas.model.vo.UserVO;
import org.buffer.agendaterapeutas.repository.IPatientRepository;
import org.buffer.agendaterapeutas.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTests {

    @Mock
    private IPatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    private PatientVO getMockPatientVO(){
        PatientVO patientVO = new PatientVO();
        UserVO user = new UserVO();
        user.setEmail("test@test.com");
        patientVO.setUser(user);
        patientVO.setHealthInsurance("Health Insurance");
        return patientVO;
    }

    @Test
    public void testCreatePatient() {
        PatientVO patientVO = getMockPatientVO();
        Patient patient = new Patient(new PatientBO(getMockPatientVO()));
        patient.setId(1L);
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        PatientVO result = patientService.createPatient(patientVO);

        assert(result.getId().equals(1L));
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    public void testCreatePatientWhenPatientExists() {
        PatientVO patientVO = getMockPatientVO();
        when(patientRepository.existsByUserEmail(anyString())).thenReturn(true);

        SchedulerException ex = assertThrows(SchedulerException.class, ()->  patientService.createPatient(patientVO));

        assert (ex.getStatusCode().equals(HttpStatus.CONFLICT));
        assert (ex.getClass().equals(UserException.class));
        verify(patientRepository, times(0)).save(any(Patient.class));
    }

    @Test
    public void testGetPatientById() {

    }

    @Test
    public void testGetPatientByIdWhenPatientDoesNotExist() {

    }

    @Test
    public void testUpdatePatient() {

    }

    @Test
    public void testUpdatePatientWhenProvidedIdsDoNotMatch() {

    }

    @Test
    public void testUpdatePatientWhenIdIsMissing() {

    }

    @Test
    public void testUpdatePatientWhenPatientIsNotFound() {

    }

    @Test
    public void testDeletePatientById() {

    }

    @Test
    public void testDeletePatientByIdWhenPatientIsNotFound() {

    }

    @Test
    public void testGetAllActivePatients() {

    }
}
