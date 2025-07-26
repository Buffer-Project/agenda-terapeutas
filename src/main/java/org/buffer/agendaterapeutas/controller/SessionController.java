package org.buffer.agendaterapeutas.controller;

import org.buffer.agendaterapeutas.exception.SchedulerException;
import org.buffer.agendaterapeutas.service.ISessionService;
import org.buffer.agendaterapeutas.model.vo.SessionVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
        SessionVO response = sessionService.createSession(sessionVO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionVO> getSessionById(@PathVariable Long id) {
        SessionVO response = sessionService.getSessionById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessionVO> updateSession(@RequestBody SessionVO session, @PathVariable Long id) {
        SessionVO response = sessionService.updateSession(session, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/cancelReservation/{id}")
    public ResponseEntity<Void> cancelSession(@PathVariable Long id) {
        sessionService.cancelSession(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<SessionVO>> getAllSessions() {
        List<SessionVO> response = sessionService.getAllSessions();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/range")
    public ResponseEntity<List<SessionVO>> getSessionsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate
    ) {
        List<SessionVO> response = sessionService.getSessionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{therapistId}")
    public ResponseEntity<List<SessionVO>> getSessionsByTherapist(@PathVariable Long therapistId) {
        List<SessionVO> response = sessionService.getSessionsByTherapistId(therapistId);
        return ResponseEntity.ok(response);
    }

}
