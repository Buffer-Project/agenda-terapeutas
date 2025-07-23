package org.buffer.agendaterapeutas.controller;

import org.buffer.agendaterapeutas.exception.SchedulerException;
import org.buffer.agendaterapeutas.model.entity.Session;
import org.buffer.agendaterapeutas.model.entity.Therapist;
import org.buffer.agendaterapeutas.model.vo.SessionVO;
import org.buffer.agendaterapeutas.service.ITherapistService;
import org.buffer.agendaterapeutas.model.vo.TherapistVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/therapist")
public class TherapistController {

    private final ITherapistService therapistService;

    public TherapistController(ITherapistService therapistService) {
        this.therapistService = therapistService;
    }

    @PostMapping
    public ResponseEntity<TherapistVO> createTherapist(@RequestBody TherapistVO therapistVO) {
        try {
            TherapistVO response = therapistService.createTherapist(therapistVO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }

    @PutMapping("/{therapistId}")
    public ResponseEntity<TherapistVO> updateTherapist(@RequestBody TherapistVO therapist, @PathVariable Long therapistId) {
        try {
            TherapistVO response = therapistService.updateTherapist(therapist, therapistId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }

    @DeleteMapping("/{therapistId}")
    public void deleteTherapist(@PathVariable Long therapistId)  {
        try {
            therapistService.deleteTherapistById(therapistId);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }

    @GetMapping("/{therapistId}")
    public ResponseEntity<TherapistVO> getTherapistById(@PathVariable Long therapistId) {
        try {
            TherapistVO response = therapistService.getTherapistById(therapistId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<TherapistVO>> getAllTherapists() {
        try {
            List<TherapistVO> response = therapistService.getAllTherapists();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }



}
