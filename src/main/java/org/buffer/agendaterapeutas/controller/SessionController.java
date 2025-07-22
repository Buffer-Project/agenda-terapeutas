package org.buffer.agendaterapeutas.controller;

import org.buffer.agendaterapeutas.exception.SchedulerException;
import org.buffer.agendaterapeutas.model.Session;
import org.buffer.agendaterapeutas.service.ISessionService;
import org.buffer.agendaterapeutas.vo.SessionVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/session")
public class SessionController {

    private final ISessionService sessionService;

    public SessionController(ISessionService sessionService) {
        this.sessionService = sessionService;
    }


    @PostMapping()
    public ResponseEntity<SessionVO> createSession(@RequestBody SessionVO sessionVO) {
        try {
            SessionVO session = sessionService.createSession(sessionVO);
            return ResponseEntity.ok(session);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionVO> getSessionById(@PathVariable Long id) {
        try{
            SessionVO session = sessionService.getSessionById(id);
            return ResponseEntity.ok(session);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<SessionVO> updateSession(@RequestBody Session session) {
        try{
            SessionVO response = sessionService.updateSession(session);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        try{
            sessionService.deleteSession(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<SessionVO>> getAllSessions() {
        try{
            List<SessionVO> sessions = sessionService.getAllSessions();
            return ResponseEntity.ok(sessions);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }
}
