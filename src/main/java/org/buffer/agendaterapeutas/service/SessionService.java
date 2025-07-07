package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.model.Session;
import org.buffer.agendaterapeutas.repository.SessionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SessionService implements ISessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Session createSession(Session session) throws Exception {
        if(sessionRepository.existsByIdSession(session.getIdSession())){
            throw new Exception("The session already exists");
        }
        return sessionRepository.save(session);
    }

    @Override
    public List<Session> listAllSessions() {
        return sessionRepository.findAll();
    }


}
