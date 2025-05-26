package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.model.Session;

import java.util.List;

public interface ISessionService {
    Session createSession(Session session) throws Exception;

    List<Session> listAllSessions();
}

