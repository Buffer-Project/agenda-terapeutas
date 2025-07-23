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
        try {
            SessionVO response = sessionService.createSession(sessionVO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionVO> getSessionById(@PathVariable Long id) {
        try {
            SessionVO response = sessionService.getSessionById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessionVO> updateSession(@RequestBody SessionVO session, @PathVariable Long id) {
        try {
            SessionVO response = sessionService.updateSession(session, id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        try {
            sessionService.deleteSession(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }

    @DeleteMapping("/cancelReservation/{id}")
    public ResponseEntity<Void> cancelSession(@PathVariable Long id) {
        try {
            sessionService.cancelSession(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<SessionVO>> getAllSessions() {
        try {
            List<SessionVO> response = sessionService.getAllSessions();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }

    @GetMapping("/range")
    public ResponseEntity<List<SessionVO>> getSessionsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate
    ) {
        try {
            List<SessionVO> response = sessionService.getSessionsByDateRange(startDate, endDate);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }

    @GetMapping("/{therapistId}")
    public ResponseEntity<List<SessionVO>> getSessionsByTherapist(@PathVariable Long therapistId) {
        try {
            List<SessionVO> response = sessionService.getSessionsByTherapistId(therapistId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }

}
