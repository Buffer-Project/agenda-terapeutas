package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.model.Session;
import org.buffer.agendaterapeutas.vo.SessionVO;

import java.util.List;

public interface ISessionService {
    Session createSession(SessionVO sessionVO);

    Session getSessionById(Long id);

    List<Session> getAllSessions();

    Session updateSession(Session session);

    void deleteSession(Long id);


}

