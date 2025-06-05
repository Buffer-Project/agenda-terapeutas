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

    /*create patient*/
    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) throws Exception {
        return patientService.createPatient(patient);
    }

    /*read patient*/
    @GetMapping("/{id}")
    public PatientVO getPatientById(@PathVariable Long id) throws Exception {
        return new PatientVO(patientService.getPatientById(id));
    }

    /*update patient*/
    @PutMapping
    public Patient updatePatient(@RequestBody Patient patient) throws Exception {
        return patientService.updatePatient(patient);
    }

    /*delete patient (not permanently)*/
    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) throws Exception {
        patientService.deletePatientById(id);
    }


    /*list patients*/
    @GetMapping
    public List<PatientVO> getAllPatients() {
        return patientService.getAllPatients()
                .stream()
                .map(PatientVO::new)
                .collect(Collectors.toList());
    }
}
