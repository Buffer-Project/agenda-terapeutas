package org.buffer.agendaterapeutas.controller;

import org.buffer.agendaterapeutas.exception.SchedulerException;
import org.buffer.agendaterapeutas.service.IPatientService;
import org.buffer.agendaterapeutas.service.impl.PatientServiceImpl;
import org.buffer.agendaterapeutas.model.vo.PatientVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {

    private final IPatientService patientService;

    public PatientController(PatientServiceImpl patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<PatientVO> createPatient(@RequestBody PatientVO patientVO) {
        try {
            PatientVO response = patientService.createPatient(patientVO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientVO> getPatientById(@PathVariable Long id) {
        try {
            PatientVO response = patientService.getPatientById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientVO> updatePatient(@RequestBody PatientVO patient, @PathVariable Long id) {
        try {
            PatientVO response = patientService.updatePatient(patient, id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        try {
            patientService.deletePatientById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<PatientVO>> getAllPatients() {
        try {
            List<PatientVO> response = patientService.getAllPatients();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }
}
