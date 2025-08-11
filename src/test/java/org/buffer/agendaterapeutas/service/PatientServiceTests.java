package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.exception.PatientException;
import org.buffer.agendaterapeutas.exception.SchedulerException;
import org.buffer.agendaterapeutas.exception.UserException;
import org.buffer.agendaterapeutas.exception.errors.PatientError;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    void testGetPatientById() {
        Long id = 1L;
        Patient entity = new Patient(new PatientBO(getMockPatientVO()));
        entity.setId(id);

        when(patientRepository.findById(id)).thenReturn(Optional.of(entity));

        PatientVO result = patientService.getPatientById(id);

        assertEquals(id, result.getId());
        assertEquals("test@test.com", result.getUser().getEmail());
        verify(patientRepository, times(1)).findById(id);
    }

    @Test
    void testGetPatientByIdWhenNotFound() {
        Long id = 99L;
        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        PatientException ex = assertThrows(PatientException.class, () -> patientService.getPatientById(id));

        assertEquals(PatientError.NOT_FOUND.getCode(), ex.getCode());
        assert (ex.getClass().equals(PatientException.class));
        verify(patientRepository, times(1)).findById(id);
    }

    @Test
    void testUpdatePatient() {
        Long id = 1L;
        PatientVO body = getMockPatientVO();
        body.setId(id);

        Patient updated = new Patient(new PatientBO(body));
        updated.setId(id);
        updated.setHealthInsurance("New Insurance");

        when(patientRepository.existsById(id)).thenReturn(true);
        when(patientRepository.save(any(Patient.class))).thenReturn(updated);

        PatientVO result = patientService.updatePatient(body, id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("New Insurance", result.getHealthInsurance());
        verify(patientRepository).existsById(id);
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    void testUpdatePatientWhenProvidedIdNull() {
        Long id = null;
        PatientVO body = getMockPatientVO();
        body.setId(1L);

        PatientException ex = assertThrows(PatientException.class, () -> patientService.updatePatient(body, id));

        assertEquals(PatientError.MISSING_ID.getCode(), ex.getCode());
        assert (ex.getClass().equals(PatientException.class));
        verify(patientRepository, never()).existsById(anyLong());
        verify(patientRepository, never()).save(any());
    }

    @Test
    void testUpdatePatientWhenBodyIdIsNull() {
        Long id = 50L;
        PatientVO body = getMockPatientVO();
        body.setId(null);

        PatientException ex = assertThrows(PatientException.class, () -> patientService.updatePatient(body, id));

        assertEquals(PatientError.MISSING_ID.getCode(), ex.getCode());
        verify(patientRepository, never()).existsById(anyLong());
        verify(patientRepository, never()).save(any());
    }

    @Test
    void testUpdatePatientWhenBodyIsNull() {
        Long id = 1L;

        PatientException ex = assertThrows(PatientException.class, () -> patientService.updatePatient(null, id));

        assertEquals(PatientError.MISSING_ID.getCode(), ex.getCode());
        verify(patientRepository, never()).existsById(anyLong());
        verify(patientRepository, never()).save(any());
    }


    @Test
    void testUpdatePatientWhenIdsConflict() {
        Long pathId = 700L;
        PatientVO body = getMockPatientVO();
        body.setId(1L);

        PatientException ex = assertThrows(PatientException.class, () -> patientService.updatePatient(body, pathId));

        assertEquals(PatientError.ID_CONFLICT.getCode(), ex.getCode());
        assert (ex.getClass().equals(PatientException.class));
        verify(patientRepository, never()).existsById(anyLong());
        verify(patientRepository, never()).save(any());
    }

    @Test
    void testUpdatePatientWhenNotFound() {
        Long id = 1L;
        PatientVO body = getMockPatientVO();
        body.setId(id);

        when(patientRepository.existsById(id)).thenReturn(false);

        PatientException ex = assertThrows(PatientException.class, () -> patientService.updatePatient(body, id));

        assertEquals(PatientError.NOT_FOUND.getCode(), ex.getCode());
        assert (ex.getClass().equals(PatientException.class));
        verify(patientRepository, times(1)).existsById(id);
        verify(patientRepository, never()).save(any());
    }

    @Test
    void testDeletePatientById() {
        Long id = 3L;
        Patient entity = new Patient(new PatientBO(getMockPatientVO()));
        entity.setId(id);

        when(patientRepository.findById(id)).thenReturn(Optional.of(entity));
        doNothing().when(patientRepository).delete(any(Patient.class));

        patientService.deletePatientById(id);

        verify(patientRepository, times(1)).findById(id);
        verify(patientRepository, times(1)).delete(entity);
    }

    @Test
    void testDeletePatientByIdWhenNotFound() {
        Long id = 20L;
        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        PatientException ex = assertThrows(PatientException.class, () -> patientService.deletePatientById(id));

        assertEquals(PatientError.NOT_FOUND.getCode(), ex.getCode());
        assert (ex.getClass().equals(PatientException.class));
        verify(patientRepository, times(1)).findById(id);
        verify(patientRepository, never()).delete(any());
    }

    @Test
    void testGetAllActivePatients() {
        Patient p1 = new Patient(new PatientBO(getMockPatientVO())); p1.setId(10L);
        Patient p2 = new Patient(new PatientBO(getMockPatientVO())); p2.setId(20L);

        when(patientRepository.findByUserActiveTrue()).thenReturn(List.of(p1, p2));

        List<PatientVO> result = patientService.getAllActivePatients();

        assertEquals(2, result.size());
        verify(patientRepository, times(1)).findByUserActiveTrue();
    }

    @Test
    void testGetAllActivePatientsWhenEmpty() {
        when(patientRepository.findByUserActiveTrue()).thenReturn(List.of());

        List<PatientVO> result = patientService.getAllActivePatients();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(patientRepository, times(1)).findByUserActiveTrue();
    }

}
