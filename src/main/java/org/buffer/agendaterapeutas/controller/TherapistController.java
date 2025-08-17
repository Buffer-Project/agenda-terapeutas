package org.buffer.agendaterapeutas.controller;

import org.buffer.agendaterapeutas.model.vo.SessionVO;
import org.buffer.agendaterapeutas.service.ISessionService;
import org.buffer.agendaterapeutas.service.ITherapistService;
import org.buffer.agendaterapeutas.model.vo.TherapistVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/therapist")
public class TherapistController {

    private final ITherapistService therapistService;
    private final ISessionService sessionService;

    public TherapistController(ITherapistService therapistService, ISessionService sessionService) {
        this.therapistService = therapistService;
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<TherapistVO> createTherapist(@RequestBody TherapistVO therapistVO) {
        TherapistVO response = therapistService.createTherapist(therapistVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TherapistVO> updateTherapist(@RequestBody TherapistVO therapist, @PathVariable Long id) {
        TherapistVO response = therapistService.updateTherapist(therapist, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTherapist(@PathVariable Long id) {
        therapistService.deleteTherapistById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TherapistVO> getTherapistById(@PathVariable Long id) {
        TherapistVO response = therapistService.getTherapistById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<TherapistVO>> getAllTherapists() {
        List<TherapistVO> response = therapistService.getAllTherapists();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/sessions")
    public ResponseEntity<List<SessionVO>> getSessionsByTherapist(@PathVariable Long id) {
        List<SessionVO> response = sessionService.getSessionsByTherapistId(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
