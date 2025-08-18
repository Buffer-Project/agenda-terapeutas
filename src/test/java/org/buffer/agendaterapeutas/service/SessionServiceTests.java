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
import org.buffer.agendaterapeutas.model.entity.Therapist;
import org.buffer.agendaterapeutas.model.entity.User;
import org.buffer.agendaterapeutas.model.vo.PatientVO;
import org.buffer.agendaterapeutas.model.vo.SessionVO;
import org.buffer.agendaterapeutas.model.vo.TherapistVO;
import org.buffer.agendaterapeutas.model.vo.UserVO;
import org.buffer.agendaterapeutas.repository.IPatientRepository;
import org.buffer.agendaterapeutas.repository.ISessionRepository;
import org.buffer.agendaterapeutas.repository.ITherapistRepository;
import org.buffer.agendaterapeutas.service.impl.SessionServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTests {

    @Mock
    private IUserService userService;

    @Mock
    private ISessionRepository sessionRepository;

    @Mock
    private IPatientService patientService;

    @Mock
    private ITherapistService therapistService;


    @InjectMocks
    private SessionServiceImpl sessionService;

    private SessionVO getMockSessionVO() {
        SessionVO sessionVO = new SessionVO();
        LocalDateTime start = LocalDateTime.now();
        sessionVO.setId(1L);
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
        user.setId(1L);
        therapistVO.setUser(user);
        therapistVO.setSpecialty(therapistVO.getSpecialty());
        return therapistVO;
    }

    @Test
    public void testCreateSession() {
        SessionVO sessionVO = getMockSessionVO();
        TherapistVO therapistVO = getMockTherapistVO();
        therapistVO.setId(1L);
        sessionVO.setTherapist(therapistVO);
        PatientVO patientVO = getMockPatientVO();
        patientVO.setId(1L);
        sessionVO.setPatient(patientVO);

        Session session = new Session(new SessionBO(sessionVO));

        when(userService.getUserById(anyLong())).thenReturn(therapistVO.getUser());
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        when(therapistService.getTherapistById(anyLong())).thenReturn(therapistVO);
        when(patientService.getPatientById(anyLong())).thenReturn(patientVO);

        sessionVO.setId(null);
        SessionVO result = sessionService.createSession(sessionVO);

        assert result.getId() == 1L;
        assert(result.getStartDateTime().equals(sessionVO.getStartDateTime()));
        assert(result.getEndDateTime().equals(sessionVO.getEndDateTime()));
        assert(result.getStatus().equals(SessionStatusEnum.RESERVED));
        verify(sessionRepository).save(any(Session.class));

    }

    @Test
    void testCreateSessionWhenTherapistNotFound() {
        SessionVO sessionVO = getMockSessionVO();

        TherapistVO therapistVO = getMockTherapistVO();
        therapistVO.setId(99L);
        sessionVO.setTherapist(therapistVO);

        PatientVO patientVO = getMockPatientVO();
        patientVO.setId(1L);
        sessionVO.setPatient(patientVO);
        sessionVO.setId(null);

        TherapistException ex = assertThrows(TherapistException.class,
                () -> sessionService.createSession(sessionVO));

        assertEquals(org.buffer.agendaterapeutas.exception.errors.TherapistError.NOT_FOUND.getCode(), ex.getCode());
        verify(sessionRepository, never()).save(any());
    }

    @Test
    void testCreateSessionWhenPatientNotFound() {
        SessionVO sessionVO = getMockSessionVO();
        sessionVO.setId(null);

        TherapistVO therapistVO = getMockTherapistVO();
        therapistVO.setId(1L);
        sessionVO.setTherapist(therapistVO);

        PatientVO patientVO = getMockPatientVO();
        patientVO.setId(77L);
        sessionVO.setPatient(patientVO);
        when(therapistService.getTherapistById(anyLong())).thenReturn(therapistVO);
        when(userService.getUserById(anyLong())).thenReturn(patientVO.getUser());

        PatientException ex = assertThrows(PatientException.class,
                () -> sessionService.createSession(sessionVO));

        assertEquals(PatientError.NOT_FOUND.getCode(), ex.getCode());
        verify(sessionRepository, never()).save(any());
    }

    @Test
    void testGetSessionById() {
        Long id = 10L;
        SessionVO base = getMockSessionVO();

        Session entity = new Session(new SessionBO(base));
        entity.setId(id);

        when(sessionRepository.findById(id)).thenReturn(Optional.of(entity));

        SessionVO result = sessionService.getSessionById(id);

        assertEquals(id, result.getId());
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
        body.setId(id);

        Session updated = new Session(new SessionBO(body));
        updated.setId(id);

        when(sessionRepository.existsById(id)).thenReturn(true);
        when(sessionRepository.save(any(Session.class))).thenReturn(updated);

        SessionVO result = sessionService.updateSession(body, id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(sessionRepository).existsById(id);
        verify(sessionRepository).save(any(Session.class));
    }

    @Test
    void testUpdateSessionWhenProvidedIdNull() {
        SessionVO body = getMockSessionVO();
        body.setId(1L);

        SessionException ex = assertThrows(SessionException.class, () -> sessionService.updateSession(body, null));
        assertEquals(SessionError.MISSING_ID.getCode(), ex.getCode());

        verify(sessionRepository, never()).existsById(anyLong());
        verify(sessionRepository, never()).save(any());
    }

    @Test
    void testUpdateSessionWhenBodyIdIsNull() {
        Long id = 50L;
        SessionVO body = getMockSessionVO();
        body.setId(null);

        SessionException ex = assertThrows(SessionException.class, () -> sessionService.updateSession(body, id));
        assertEquals(SessionError.MISSING_ID.getCode(), ex.getCode());

        verify(sessionRepository, never()).existsById(anyLong());
        verify(sessionRepository, never()).save(any());
    }

    @Test
    void testUpdateSessionWhenIdsConflict() {
        Long pathId = 700L;
        SessionVO body = getMockSessionVO();
        body.setId(1L);

        SessionException ex = assertThrows(SessionException.class, () -> sessionService.updateSession(body, pathId));
        assertEquals(SessionError.ID_CONFLICT.getCode(), ex.getCode());

        verify(sessionRepository, never()).existsById(anyLong());
        verify(sessionRepository, never()).save(any());
    }

    @Test
    void testUpdateSessionWhenNotFound() {
        Long id = 1L;
        SessionVO body = getMockSessionVO();
        body.setId(id);

        when(sessionRepository.existsById(id)).thenReturn(false);

        SessionException ex = assertThrows(SessionException.class, () -> sessionService.updateSession(body, id));
        assertEquals(SessionError.NOT_FOUND.getCode(), ex.getCode());

        verify(sessionRepository).existsById(id);
        verify(sessionRepository, never()).save(any());
    }

    @Test
    @Disabled("moved to PATCH update, TODO: fix")
    void testDeleteSession() {
        Long id = 3L;
        SessionVO base = getMockSessionVO();
        Session entity = new Session(new SessionBO(base));
        entity.setId(id);

        when(sessionRepository.findById(id)).thenReturn(Optional.of(entity));
        doNothing().when(sessionRepository).deleteById(id);

        sessionService.deleteSession(id);

        verify(sessionRepository).findById(id);
        verify(sessionRepository).deleteById(id);
    }

    @Test
    @Disabled("moved to PATCH update, TODO: fix")
    void testDeleteSessionWhenNotFound() {
        Long id = 20L;
        when(sessionRepository.findById(id)).thenReturn(Optional.empty());

        SessionException ex = assertThrows(SessionException.class, () -> sessionService.deleteSession(id));
        assertEquals(SessionError.NOT_FOUND.getCode(), ex.getCode());

        verify(sessionRepository).findById(id);
        verify(sessionRepository, never()).deleteById(anyLong());
    }

    @Test
    @Disabled("moved to PATCH update, TODO: fix")
    void testCancelSessionInTheFuture() {
        Long id = 9L;
        Session future = new Session();
        future.setId(id);
        future.setStartDateTime(LocalDateTime.now().plusHours(2));
        future.setEndDateTime(LocalDateTime.now().plusHours(3));
        future.setStatus(SessionStatusEnum.RESERVED);

        when(sessionRepository.findById(id)).thenReturn(Optional.of(future));
        when(sessionRepository.save(any(Session.class))).thenAnswer(inv -> inv.getArgument(0));

//        sessionService.cancelSession(id);

        verify(sessionRepository).findById(id);
        verify(sessionRepository).save(argThat(s -> s.getStatus() == SessionStatusEnum.CANCELLED));
    }

    @Test
    @Disabled("moved to PATCH update, TODO: fix")
    void testCancelSessionInThePast() {
        Long id = 10L;
        Session past = new Session();
        past.setId(id);
        past.setStartDateTime(LocalDateTime.now().minusHours(1));
        past.setEndDateTime(LocalDateTime.now().minusMinutes(30));
        past.setStatus(SessionStatusEnum.RESERVED);

        when(sessionRepository.findById(id)).thenReturn(Optional.of(past));

//        SessionException ex = assertThrows(SessionException.class, () -> sessionService.cancelSession(id));
//        assertEquals(SessionError.CANNOT_CANCEL_PAST_SESSION.getCode(), ex.getCode());

        verify(sessionRepository).findById(id);
        verify(sessionRepository, never()).save(any());
    }

    @Test
    @Disabled("moved to PATCH update, TODO: fix")
    void testCancelSessionWhenNotFound() {
        Long id = 404L;
        when(sessionRepository.findById(id)).thenReturn(Optional.empty());

//        SessionException ex = assertThrows(SessionException.class, () -> sessionService.cancelSession(id));
//        assertEquals(SessionError.NOT_FOUND.getCode(), ex.getCode());

        verify(sessionRepository).findById(id);
        verify(sessionRepository, never()).save(any());
    }

    @Test
    public void testGetAllSessions() {

        Session session1 = new Session();
        session1.setId(1L);
        session1.setStartDateTime(LocalDateTime.now());
        session1.setEndDateTime(LocalDateTime.now().plusHours(1));
        session1.setStatus(SessionStatusEnum.RESERVED);

        Session session2 = new Session();
        session2.setId(2L);
        session2.setStartDateTime(LocalDateTime.now().plusDays(1));
        session2.setEndDateTime(LocalDateTime.now().plusDays(1).plusHours(1));
        session2.setStatus(SessionStatusEnum.RESERVED);

        List<Session> sessionList = List.of(session1, session2);

        when(sessionRepository.findAll()).thenReturn(sessionList);

        List<SessionVO> result = sessionService.getAllSessions(new HashMap<>());

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());

        verify(sessionRepository).findAll();
    }

    @Test
    @Disabled("moved to get with params, TODO fix")
    void testGetSessionsByDateRange() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);

    }

}
