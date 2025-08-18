package org.buffer.agendaterapeutas.controller;

import org.buffer.agendaterapeutas.service.IPatientService;
import org.buffer.agendaterapeutas.model.vo.PatientVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {

    private final IPatientService patientService;

    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<PatientVO> createPatient(@RequestBody PatientVO patientVO) {
        PatientVO response = patientService.createPatient(patientVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientVO> getPatientById(@PathVariable Long id) {
        PatientVO response = patientService.getPatientById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientVO> updatePatient(@RequestBody PatientVO patient, @PathVariable Long id) {
        PatientVO response = patientService.updatePatient(patient, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatientById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<PatientVO>> getAllActivePatients() {
        List<PatientVO> response = patientService.getAllActivePatients();
        return ResponseEntity.ok(response);
    }
}
