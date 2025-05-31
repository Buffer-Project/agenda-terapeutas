package org.buffer.agendaterapeutas.controller;

import org.buffer.agendaterapeutas.service.IPatientService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {

    public PatientController(IPatientService patientService) {
    }


}
