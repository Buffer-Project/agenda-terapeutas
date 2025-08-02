package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.enums.SessionStatusEnum;
import org.buffer.agendaterapeutas.exception.SessionException;
import org.buffer.agendaterapeutas.exception.errors.SessionError;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    public void testCreateSessionWithExistingId() {
        SessionVO sessionVO = getMockSessionVO();
        sessionVO.setIdSession(1L);

        Therapist therapist = new Therapist();
        therapist.setId(1L);
        Patient patient = new Patient();
        patient.setId(1L);

        Session session = new Session(new SessionBO(sessionVO));
        session.setIdSession(1L);

        when(therapistRepository.findById(anyLong())).thenReturn(Optional.of(therapist));
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        SessionVO result = sessionService.createSession(sessionVO);

        assertNotNull(result);
        assertEquals(1L, result.getIdSession());
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
    public void testGetSessionsByDateRange() {
        LocalDateTime start = LocalDateTime.of(2025, 8, 1, 10, 0);
        LocalDateTime end = start.plusDays(7);

        Session session = new Session();
        session.setIdSession(1L);
        session.setStartDateTime(start.plusDays(1));
        session.setEndDateTime(start.plusDays(1).plusHours(1));
        session.setStatus(SessionStatusEnum.RESERVED);

        when(sessionRepository.findByStartDateTimeBetween(start, end))
                .thenReturn(List.of(session));

        List<SessionVO> result = sessionService.getSessionsByDateRange(start, end);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getIdSession());
        verify(sessionRepository).findByStartDateTimeBetween(start, end);
    }

    @Test
    public void testCancelSessionSuccess() {
        Session session = new Session();
        session.setIdSession(1L);
        session.setStartDateTime(LocalDateTime.now().plusDays(1));
        session.setStatus(SessionStatusEnum.RESERVED);

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(sessionRepository.save(session)).thenReturn(session);

        sessionService.cancelSession(1L);

        assertEquals(SessionStatusEnum.CANCELED, session.getStatus());
        verify(sessionRepository).save(session);
    }

    @Test
    public void testCancelSessionNotFound() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());

        SessionException ex = assertThrows(SessionException.class, () -> {
            sessionService.cancelSession(1L);
        });

        assertEquals("Session 100 not found", ex.getMessage());
        verify(sessionRepository, never()).save(any());
    }

    @Test
    public void testCancelSessionInThePast() {
        Session session = new Session();
        session.setIdSession(1L);
        session.setStartDateTime(LocalDateTime.now().minusDays(1));
        session.setStatus(SessionStatusEnum.RESERVED);

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        SessionException ex = assertThrows(SessionException.class, () -> {
            sessionService.cancelSession(1L);
        });

        assertTrue(ex.getMessage().contains("Past sessions cannot be cancelled"));
        verify(sessionRepository, never()).save(any());
    }

    @Test
    public void testGetAllSessions() {

        Session session1 = new Session();
        session1.setIdSession(1L);
        session1.setStartDateTime(LocalDateTime.now());
        session1.setEndDateTime(LocalDateTime.now().plusHours(1));
        session1.setStatus(SessionStatusEnum.RESERVED);

        Session session2 = new Session();
        session2.setIdSession(2L);
        session2.setStartDateTime(LocalDateTime.now().plusDays(1));
        session2.setEndDateTime(LocalDateTime.now().plusDays(1).plusHours(1));
        session2.setStatus(SessionStatusEnum.RESERVED);

        List<Session> sessionList = List.of(session1, session2);

        when(sessionRepository.findAll()).thenReturn(sessionList);

        List<SessionVO> result = sessionService.getAllSessions();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getIdSession());
        assertEquals(2L, result.get(1).getIdSession());

        verify(sessionRepository).findAll();
    }

}
