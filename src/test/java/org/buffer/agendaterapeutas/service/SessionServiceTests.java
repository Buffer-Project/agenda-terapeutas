package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.model.vo.SessionVO;
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

    private SessionVO getMockSessionVO(){
        SessionVO sessionVO = new SessionVO();
        sessionVO.setStartTime(LocalDateTime.now());
        sessionVO.setEndTime(LocalDateTime.now().plusHours(1));
        sessionVO.setTherapistId(10L);
        sessionVO.setCancelled(false);
        return sessionVO;


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
