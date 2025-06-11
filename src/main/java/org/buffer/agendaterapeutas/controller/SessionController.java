package org.buffer.agendaterapeutas.controller;

import org.buffer.agendaterapeutas.model.Session;
import org.buffer.agendaterapeutas.service.SessionService;
import org.buffer.agendaterapeutas.vo.SessionVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/session")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public Session createSession(@RequestBody SessionVO sessionVO) throws Exception {
        return sessionService.createSession(sessionVO);
    }

    @GetMapping("/{id}")
    public SessionVO getSessionById(@PathVariable Long id) throws Exception {
        return new SessionVO(sessionService.getSessionById(id));
    }

    @PutMapping
    public Session updateSession(@RequestBody Session session) throws Exception {
        return sessionService.updateSession(session);
    }

    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable Long id) throws Exception {
        sessionService.deleteSession(id);
    }

    @GetMapping
    public List<SessionVO> getAllSessions() {
        return sessionService.getAllSessions()
                .stream()
                .map(SessionVO::new)
                .collect(Collectors.toList());
    }
}
