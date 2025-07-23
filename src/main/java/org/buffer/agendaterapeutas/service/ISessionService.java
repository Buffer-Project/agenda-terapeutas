package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.model.vo.SessionVO;

import java.time.LocalDateTime;
import java.util.List;

public interface ISessionService {
    SessionVO createSession(SessionVO sessionVO);

    SessionVO getSessionById(Long id);

    List<SessionVO> getAllSessions();

    List<SessionVO> getSessionsByDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime);

    SessionVO updateSession(SessionVO session,Long id);

    void deleteSession(Long id);

    void cancelSession(Long id);

    List<SessionVO> getSessionsByTherapistId(Long therapistId);



}

