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
        TherapistVO response = therapistService.createTherapist(therapistVO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{therapistId}")
    public ResponseEntity<TherapistVO> updateTherapist(@RequestBody TherapistVO therapist, @PathVariable Long therapistId) {
        TherapistVO response = therapistService.updateTherapist(therapist, therapistId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{therapistId}")
    public void deleteTherapist(@PathVariable Long therapistId)  {
        therapistService.deleteTherapistById(therapistId);
    }

    @GetMapping("/{therapistId}")
    public ResponseEntity<TherapistVO> getTherapistById(@PathVariable Long therapistId) {
        TherapistVO response = therapistService.getTherapistById(therapistId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<TherapistVO>> getAllTherapists() {
        List<TherapistVO> response = therapistService.getAllTherapists();
        return ResponseEntity.ok(response);
    }

}
