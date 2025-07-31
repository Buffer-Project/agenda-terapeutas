package org.buffer.agendaterapeutas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.buffer.agendaterapeutas.model.vo.PatientVO;
import org.buffer.agendaterapeutas.model.vo.UserVO;
import org.buffer.agendaterapeutas.service.IPatientService;
import org.buffer.agendaterapeutas.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatientControllerTests {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    private final PatientServiceImpl patientService = mock(PatientServiceImpl.class);


    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PatientController(patientService)).build();
    }

    @Test
    public void testCreatePatient() throws Exception {
        PatientVO patientVO = mock(PatientVO.class);
        when(patientService.createPatient(any())).thenReturn(patientVO);

        mockMvc.perform(post("/api/v1/patient")
                .content(objectMapper.writeValueAsString(patientVO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isCreated(),
                content().json(objectMapper.writeValueAsString(patientVO))
        );

        verify(patientService).createPatient(any(PatientVO.class));
    }
}
