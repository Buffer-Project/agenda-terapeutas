package org.buffer.agendaterapeutas.controller;

import org.buffer.agendaterapeutas.model.Patient;
import org.buffer.agendaterapeutas.service.PatientService;
import org.buffer.agendaterapeutas.vo.PatientVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public Patient createPatient(@RequestBody PatientVO patientVO) throws Exception {
        return patientService.createPatient(patientVO);
    }

    @GetMapping("/{id}")
    public PatientVO getPatientById(@PathVariable Long id) throws Exception {
        return new PatientVO(patientService.getPatientById(id));
    }

    @PutMapping
    public Patient updatePatient(@RequestBody Patient patient) throws Exception {
        return patientService.updatePatient(patient);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) throws Exception {
        patientService.deletePatientById(id);
    }

    @GetMapping
    public List<PatientVO> getAllPatients() {
        return patientService.getAllPatients()
                .stream()
                .map(PatientVO::new)
                .collect(Collectors.toList());
    }
}
