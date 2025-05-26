package org.buffer.agendaterapeutas.controller;

import org.buffer.agendaterapeutas.model.Session;
import org.buffer.agendaterapeutas.service.ISessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/session")
public class SessionController {

    private final ISessionService sessionService;

    //constructor
    public SessionController(ISessionService sessionService) {
        this.sessionService = sessionService;

    }
    @PostMapping
    public Session createSession(Session session) throws Exception {
        return sessionService.createSession(session);
    }

    //connecting button from frontend (BringSessions)
    @GetMapping //api/v1/session
    public ResponseEntity<List<Session>> listAllSessions() {
        return ResponseEntity.ok(sessionService.listAllSessions());
    }
}
