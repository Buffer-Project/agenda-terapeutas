package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.enums.SessionStatusEnum;
import org.buffer.agendaterapeutas.model.bo.PatientBO;
import org.buffer.agendaterapeutas.model.bo.SessionBO;
import org.buffer.agendaterapeutas.model.bo.TherapistBO;
import org.buffer.agendaterapeutas.model.entity.Patient;
import org.buffer.agendaterapeutas.model.entity.Session;
import org.buffer.agendaterapeutas.model.entity.Specialty;
import org.buffer.agendaterapeutas.model.entity.Therapist;
import org.buffer.agendaterapeutas.model.vo.PatientVO;
import org.buffer.agendaterapeutas.model.vo.SessionVO;
import org.buffer.agendaterapeutas.model.vo.TherapistVO;
import org.buffer.agendaterapeutas.model.vo.UserVO;
import org.buffer.agendaterapeutas.repository.IPatientRepository;
import org.buffer.agendaterapeutas.repository.ISessionRepository;
import org.buffer.agendaterapeutas.repository.ITherapistRepository;
import org.buffer.agendaterapeutas.service.impl.SessionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTests {

    @Mock
    private ISessionRepository sessionRepository;

    @Mock
    private IPatientRepository patientRepository;

    @Mock
    private ITherapistRepository therapistRepository;


    @InjectMocks
    private SessionServiceImpl sessionService;

    private SessionVO getMockSessionVO() {
        SessionVO sessionVO = new SessionVO();
        LocalDateTime start = LocalDateTime.now();
        sessionVO.setIdSession(1L);
        sessionVO.setStartDateTime(start);
        sessionVO.setEndDateTime(sessionVO.getStartDateTime().plusHours(1));
        sessionVO.setTherapist(mock(TherapistVO.class));
        sessionVO.setPatient(mock(PatientVO.class));
        sessionVO.setStatus(SessionStatusEnum.RESERVED);
        return sessionVO;
    }

    private PatientVO getMockPatientVO() {
        PatientVO patientVO = new PatientVO();
        UserVO user = new UserVO();
        user.setEmail("test@test.com");
        patientVO.setUser(user);
        patientVO.setHealthInsurance("Health Insurance");
        return patientVO;
    }

    private TherapistVO getMockTherapistVO() {
        TherapistVO therapistVO = new TherapistVO();
        UserVO user = new UserVO();
        user.setEmail("test@test.com");
        therapistVO.setUser(user);
        therapistVO.setSpecialty(therapistVO.getSpecialty());
        return therapistVO;
    }

    /*
    SessionVO createSession(SessionVO sessionVO);
    SessionVO getSessionById(Long id);
    List<SessionVO> getAllSessions();
    List<SessionVO> getSessionsByDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime);
    SessionVO updateSession(SessionVO session,Long id);
    void deleteSession(Long id);
    void cancelSession(Long id);
    List<SessionVO> getSessionsByTherapistId(Long therapistId);
    * */

    @Test
    public void testCreateSession() {
        SessionVO sessionVO = getMockSessionVO();
        TherapistVO therapistVO = getMockTherapistVO();
        PatientVO patientVO = getMockPatientVO();

        Session session = new Session(new SessionBO(sessionVO));
        Therapist therapist = new Therapist(new TherapistBO(therapistVO));
        Patient patient = new Patient(new PatientBO(patientVO));

        when(therapistRepository.findById(anyLong())).thenReturn(Optional.of(therapist));
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        SessionVO result = sessionService.createSession(sessionVO);

        assert result.getIdSession() == 1L;
        assert(result.getStartDateTime().equals(sessionVO.getStartDateTime()));
        assert(result.getEndDateTime().equals(sessionVO.getEndDateTime()));
        assert(result.getStatus().equals(SessionStatusEnum.RESERVED));
        verify(sessionRepository).save(any(Session.class));

    }

    @Test
    public void testCreateSessionWhenSessionExists() {

    }


    @Test
    public void getSessionById() {

    }

    @Test
    public void getAllSessions() {

    }

    @Test
    public void getSessionByDateRange() {

    }
}
