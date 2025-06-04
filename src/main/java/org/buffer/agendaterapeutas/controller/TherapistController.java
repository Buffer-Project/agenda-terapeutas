package org.buffer.agendaterapeutas.controller;

import org.buffer.agendaterapeutas.model.Therapist;
import org.buffer.agendaterapeutas.service.ITherapistService;
import org.buffer.agendaterapeutas.vo.TherapistVO;
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
    public Therapist createTherapist(@RequestBody Therapist therapist) throws Exception {
        return therapistService.createTherapist(therapist);
    }

    @PutMapping
    public Therapist updateTherapist(@RequestBody Therapist therapist) throws Exception {
        return therapistService.updateTherapist(therapist);
    }

    @DeleteMapping("/{therapistId}")
    public void deleteTherapist(@PathVariable Long therapistId) throws Exception {
        therapistService.deleteTherapistById(therapistId);
    }

    @GetMapping("/{therapistId}")
    public TherapistVO getTherapistById(@PathVariable Long therapistId) throws Exception {
        return new TherapistVO(therapistService.getTherapistById(therapistId));
    }

    @GetMapping
    public List<TherapistVO> getAllTherapists() {
        return therapistService.getAllTherapists()
                .stream()
                .map(TherapistVO::new)
                .collect(Collectors.toList());
    }

}
