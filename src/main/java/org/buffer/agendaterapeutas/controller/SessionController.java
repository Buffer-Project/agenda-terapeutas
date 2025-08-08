package org.buffer.agendaterapeutas.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.buffer.agendaterapeutas.model.vo.SessionVO;
import org.buffer.agendaterapeutas.service.ISessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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


    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateSessionValue(@PathVariable Long id, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        sessionService.updateSessionValue(id, patch);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<SessionVO>> getAllSessions(@RequestParam Map<String, String> params) {
        List<SessionVO> response = sessionService.getAllSessions(params);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/therapist/{therapistId}")
    public ResponseEntity<List<SessionVO>> getSessionsByTherapist(@PathVariable Long therapistId) {
        List<SessionVO> response = sessionService.getSessionsByTherapistId(therapistId);
        return ResponseEntity.ok(response);
    }

}
