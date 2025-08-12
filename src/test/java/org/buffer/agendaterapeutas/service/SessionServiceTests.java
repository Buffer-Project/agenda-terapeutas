package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.enums.SessionStatusEnum;
import org.buffer.agendaterapeutas.exception.PatientException;
import org.buffer.agendaterapeutas.exception.SessionException;
import org.buffer.agendaterapeutas.exception.TherapistException;
import org.buffer.agendaterapeutas.exception.errors.PatientError;
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
    void testCreateSessionWhenTherapistNotFound() {
        SessionVO sessionVO = getMockSessionVO();

        TherapistVO therapistVO = new TherapistVO();
        therapistVO.setId(99L);
        sessionVO.setTherapist(therapistVO);

        PatientVO patientVO = new PatientVO();
        patientVO.setId(1L);
        sessionVO.setPatient(patientVO);

        when(therapistRepository.findById(99L)).thenReturn(Optional.empty());

        TherapistException ex = assertThrows(TherapistException.class,
                () -> sessionService.createSession(sessionVO));

        assertEquals(org.buffer.agendaterapeutas.exception.errors.TherapistError.NOT_FOUND.getCode(), ex.getCode());
        verify(therapistRepository).findById(99L);
        verify(patientRepository, never()).findById(anyLong());
        verify(sessionRepository, never()).save(any());
    }

    @Test
    void testCreateSessionWhenPatientNotFound() {
        SessionVO sessionVO = getMockSessionVO();

        TherapistVO therapistVO = new TherapistVO();
        therapistVO.setId(1L);
        sessionVO.setTherapist(therapistVO);

        PatientVO patientVO = new PatientVO();
        patientVO.setId(77L);
        sessionVO.setPatient(patientVO);

        Therapist therapist = new Therapist();
        therapist.setId(1L);

        when(therapistRepository.findById(1L)).thenReturn(Optional.of(therapist));
        when(patientRepository.findById(77L)).thenReturn(Optional.empty());

        PatientException ex = assertThrows(PatientException.class,
                () -> sessionService.createSession(sessionVO));

        assertEquals(PatientError.NOT_FOUND.getCode(), ex.getCode());
        verify(therapistRepository).findById(1L);
        verify(patientRepository).findById(77L);
        verify(sessionRepository, never()).save(any());
    }

    @Test
    void testGetSessionById() {
        Long id = 10L;
        SessionVO base = getMockSessionVO();

        Session entity = new Session(new SessionBO(base));
        entity.setIdSession(id);

        when(sessionRepository.findById(id)).thenReturn(Optional.of(entity));

        SessionVO result = sessionService.getSessionById(id);

        assertEquals(id, result.getIdSession());
        verify(sessionRepository).findById(id);
    }

    @Test
    void testGetSessionByIdWhenNotFound() {
        Long id = 1L;
        when(sessionRepository.findById(id)).thenReturn(Optional.empty());

        SessionException ex = assertThrows(SessionException.class, () -> sessionService.getSessionById(id));
        assertEquals(SessionError.NOT_FOUND.getCode(), ex.getCode());

        verify(sessionRepository).findById(id);
    }

    @Test
    void testUpdateSession() {
        Long id = 1L;
        SessionVO body = getMockSessionVO();
        body.setIdSession(id);

        Session updated = new Session(new SessionBO(body));
        updated.setIdSession(id);

        when(sessionRepository.existsById(id)).thenReturn(true);
        when(sessionRepository.save(any(Session.class))).thenReturn(updated);

        SessionVO result = sessionService.updateSession(body, id);

        assertNotNull(result);
        assertEquals(id, result.getIdSession());
        verify(sessionRepository).existsById(id);
        verify(sessionRepository).save(any(Session.class));
    }

    @Test
    void testUpdateSessionWhenProvidedIdNull() {
        SessionVO body = getMockSessionVO();
        body.setIdSession(1L);

        SessionException ex = assertThrows(SessionException.class, () -> sessionService.updateSession(body, null));
        assertEquals(SessionError.MISSING_ID.getCode(), ex.getCode());

        verify(sessionRepository, never()).existsById(anyLong());
        verify(sessionRepository, never()).save(any());
    }

    @Test
    void testUpdateSessionWhenBodyIdIsNull() {
        Long id = 50L;
        SessionVO body = getMockSessionVO();
        body.setIdSession(null);

        SessionException ex = assertThrows(SessionException.class, () -> sessionService.updateSession(body, id));
        assertEquals(SessionError.MISSING_ID.getCode(), ex.getCode());

        verify(sessionRepository, never()).existsById(anyLong());
        verify(sessionRepository, never()).save(any());
    }

    @Test
    void testUpdateSessionWhenIdsConflict() {
        Long pathId = 700L;
        SessionVO body = getMockSessionVO();
        body.setIdSession(1L);

        SessionException ex = assertThrows(SessionException.class, () -> sessionService.updateSession(body, pathId));
        assertEquals(SessionError.ID_CONFLICT.getCode(), ex.getCode());

        verify(sessionRepository, never()).existsById(anyLong());
        verify(sessionRepository, never()).save(any());
    }

    @Test
    void testUpdateSessionWhenNotFound() {
        Long id = 1L;
        SessionVO body = getMockSessionVO();
        body.setIdSession(id);

        when(sessionRepository.existsById(id)).thenReturn(false);

        SessionException ex = assertThrows(SessionException.class, () -> sessionService.updateSession(body, id));
        assertEquals(SessionError.NOT_FOUND.getCode(), ex.getCode());

        verify(sessionRepository).existsById(id);
        verify(sessionRepository, never()).save(any());
    }

    @Test
    void testDeleteSession() {
        Long id = 3L;
        SessionVO base = getMockSessionVO();
        Session entity = new Session(new SessionBO(base));
        entity.setIdSession(id);

        when(sessionRepository.findById(id)).thenReturn(Optional.of(entity));
        doNothing().when(sessionRepository).deleteById(id);

        sessionService.deleteSession(id);

        verify(sessionRepository).findById(id);
        verify(sessionRepository).deleteById(id);
    }

    @Test
    void testDeleteSessionWhenNotFound() {
        Long id = 20L;
        when(sessionRepository.findById(id)).thenReturn(Optional.empty());

        SessionException ex = assertThrows(SessionException.class, () -> sessionService.deleteSession(id));
        assertEquals(SessionError.NOT_FOUND.getCode(), ex.getCode());

        verify(sessionRepository).findById(id);
        verify(sessionRepository, never()).deleteById(anyLong());
    }

    @Test
    void testCancelSessionInTheFuture() {
        Long id = 9L;
        Session future = new Session();
        future.setIdSession(id);
        future.setStartDateTime(LocalDateTime.now().plusHours(2));
        future.setEndDateTime(LocalDateTime.now().plusHours(3));
        future.setStatus(SessionStatusEnum.RESERVED);

        when(sessionRepository.findById(id)).thenReturn(Optional.of(future));
        when(sessionRepository.save(any(Session.class))).thenAnswer(inv -> inv.getArgument(0));

        sessionService.cancelSession(id);

        verify(sessionRepository).findById(id);
        verify(sessionRepository).save(argThat(s -> s.getStatus() == SessionStatusEnum.CANCELED));
    }

    @Test
    void testCancelSessionInThePast() {
        Long id = 10L;
        Session past = new Session();
        past.setIdSession(id);
        past.setStartDateTime(LocalDateTime.now().minusHours(1));
        past.setEndDateTime(LocalDateTime.now().minusMinutes(30));
        past.setStatus(SessionStatusEnum.RESERVED);

        when(sessionRepository.findById(id)).thenReturn(Optional.of(past));

        SessionException ex = assertThrows(SessionException.class, () -> sessionService.cancelSession(id));
        assertEquals(SessionError.CANNOT_CANCEL_PAST_SESSION.getCode(), ex.getCode());

        verify(sessionRepository).findById(id);
        verify(sessionRepository, never()).save(any());
    }

    @Test
    void testCancelSessionWhenNotFound() {
        Long id = 404L;
        when(sessionRepository.findById(id)).thenReturn(Optional.empty());

        SessionException ex = assertThrows(SessionException.class, () -> sessionService.cancelSession(id));
        assertEquals(SessionError.NOT_FOUND.getCode(), ex.getCode());

        verify(sessionRepository).findById(id);
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

    @Test
    void testGetSessionsByDateRange() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);

        Session s = new Session(); s.setIdSession(11L);
        when(sessionRepository.findByStartDateTimeBetween(start, end)).thenReturn(List.of(s));

        List<SessionVO> result = sessionService.getSessionsByDateRange(start, end);

        assertEquals(1, result.size());
        assertEquals(11L, result.getFirst().getIdSession());
        verify(sessionRepository).findByStartDateTimeBetween(start, end);
    }

    @Test
    void testGetSessionsByTherapistId() {
        Long therapistId = 5L;
        Session s1 = new Session(); s1.setIdSession(100L);
        when(sessionRepository.findByTherapistId(therapistId)).thenReturn(List.of(s1));

        List<SessionVO> result = sessionService.getSessionsByTherapistId(therapistId);

        assertEquals(1, result.size());
        assertEquals(100L, result.getFirst().getIdSession());
        verify(sessionRepository).findByTherapistId(therapistId);
    }
}
