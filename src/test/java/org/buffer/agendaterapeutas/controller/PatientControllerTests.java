package org.buffer.agendaterapeutas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.buffer.agendaterapeutas.model.vo.PatientVO;
import org.buffer.agendaterapeutas.model.vo.UserVO;
import org.buffer.agendaterapeutas.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

public class PatientControllerTests {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    private final PatientServiceImpl patientService = mock(PatientServiceImpl.class);


    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new PatientController(patientService))
                .build();
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

    @Test
    public void testGetPatientById() throws Exception {
        PatientVO patientVO = new PatientVO();
        patientVO.setId(1L);
        UserVO user = new UserVO();
        user.setEmail("john@doe.com");
        patientVO.setUser(user);

        when(patientService.getPatientById(1L)).thenReturn(patientVO);

        mockMvc.perform(get("/api/v1/patient/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.id", is(1)))
                .andExpect( jsonPath("$.user.email", is("john@doe.com")));

        verify(patientService).getPatientById(1L);
    }


    @Test
    public void testUpdatePatient() throws Exception {
        Long id = 1L;
        PatientVO body = mock(PatientVO.class);
        PatientVO updated = mock(PatientVO.class);

        when(patientService.updatePatient(any(PatientVO.class), eq(id))).thenReturn(updated);

        mockMvc.perform(put("/api/v1/patient/{id}", id)
                .content(objectMapper.writeValueAsString(body))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk(),
                content().json(objectMapper.writeValueAsString(updated))
        );

        verify(patientService).updatePatient(any(PatientVO.class), eq(id));
    }

    @Test
    public void testDeletePatient() throws Exception {
        doNothing().when(patientService).deletePatientById(1L);

        mockMvc.perform(delete("/api/v1/patient/{id}", 1L))
                .andExpect(status().isOk());

        verify(patientService).deletePatientById(1L);
    }

    @Test
    public void testGetAllActivePatients() throws Exception {
        PatientVO p1 = new PatientVO();
        p1.setId(1L);
        UserVO u1 = new UserVO();
        u1.setEmail("johndoe@mail.com");
        p1.setUser(u1);

        PatientVO p2 = new PatientVO();
        p2.setId(2L);
        UserVO u2 = new UserVO();
        u2.setEmail("jamescharles@mail.com");
        p2.setUser(u2);

        when(patientService.getAllActivePatients()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/api/v1/patient/findAll")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json(objectMapper.writeValueAsString(List.of(p1, p2)))

                );

        verify(patientService).getAllActivePatients();
    }

}

