package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.model.Patient;
import org.buffer.agendaterapeutas.model.User;
import org.buffer.agendaterapeutas.repository.PatientRepository;
import org.buffer.agendaterapeutas.vo.PatientVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTests {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;
    /*
    * 3 fases en cada test:
    *   1: Armado de variables necesarias y preparacion de mocks
    *   2: Invocacion de la logica a testear
    *   3: Validacion de resultados
    * */


    private Patient getMockPatient(){
        Patient expectedPatient = new Patient();
        User user = new User();
        user.setEmail("<EMAIL>");
        expectedPatient.setUser(user);
        return expectedPatient;
    }

    private PatientVO getMockPatientVO(){
        return new PatientVO(getMockPatient());
    }


    @Test
    void createPatientWhenPatientDoesNotExistTest() throws Exception {
//    1: Armado de variables necesarias
        PatientVO patientVO = new PatientVO(getMockPatient());

        when(patientRepository.existsByUserEmail(patientVO.getEmail())).thenReturn(false);
        when(patientRepository.save(any(Patient.class))).thenAnswer(i -> i.getArguments()[0]);;

//    2: Invocacion de la logica a testear
        patientService.createPatient(patientVO);

//    3: Validacion de resultados
        verify(patientRepository, times(1)).save(any(Patient.class));
    }


    @Test
    void createPatientWhenPatientExistsTest() {
//    1: Armado de variables necesarias
        PatientVO patientVO = getMockPatientVO();
        when(patientRepository.existsByUserEmail(patientVO.getEmail())).thenReturn(true);

//    2: Invocacion de la logica a testear / 3: Validacion de resultados
        assertThrows(Exception.class, () -> patientService.createPatient(patientVO));

    }

    @Test
    void getPatientByIdTest() throws Exception {

    }

    @Test
    void updatePatientTest() throws Exception {

    }

    @Test
    void deletePatientByIdTest() throws Exception{

    }

    @Test
    void getAllPatientsTest(){

    }
}
