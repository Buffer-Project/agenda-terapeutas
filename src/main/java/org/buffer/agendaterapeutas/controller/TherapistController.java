package org.buffer.agendaterapeutas.controller;

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

    public TherapistController(ITherapistService therapistService) {
        this.therapistService = therapistService;
    }

    @PostMapping
    public ResponseEntity<TherapistVO> createTherapist(@RequestBody TherapistVO therapistVO) {
        TherapistVO response = therapistService.createTherapist(therapistVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TherapistVO> updateTherapist(@RequestBody TherapistVO therapist, @PathVariable Long id) {
        TherapistVO response = therapistService.updateTherapist(therapist, id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTherapist(@PathVariable Long id) {
        therapistService.deleteTherapistById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TherapistVO> getTherapistById(@PathVariable Long id) {
        TherapistVO response = therapistService.getTherapistById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<TherapistVO>> getAllTherapists() {
        List<TherapistVO> response = therapistService.getAllTherapists();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
