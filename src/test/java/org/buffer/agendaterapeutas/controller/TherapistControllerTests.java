package org.buffer.agendaterapeutas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.buffer.agendaterapeutas.model.vo.TherapistVO;
import org.buffer.agendaterapeutas.service.ISessionService;
import org.buffer.agendaterapeutas.service.impl.TherapistServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class TherapistControllerTests {


    private final ObjectMapper objectMapper = new ObjectMapper();


    private MockMvc mockMvc;
    private final ISessionService sessionService = mock(ISessionService.class);
    private final TherapistServiceImpl therapistService = mock(TherapistServiceImpl.class);

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TherapistController(therapistService,sessionService)).build();
    }


    @Test
    void createTherapistTest() throws Exception {
        TherapistVO therapistVO = mock(TherapistVO.class);

        when(therapistService.createTherapist(any())).thenReturn(therapistVO);

        mockMvc.perform(post("/api/v1/therapist")
                .content(objectMapper.writeValueAsString(therapistVO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isCreated(),
                content().json(objectMapper.writeValueAsString(therapistVO))
        );
        verify(therapistService).createTherapist(any(TherapistVO.class));
    }

    @Test
    void updateTherapistTest() throws Exception {
        TherapistVO therapistVO = mock(TherapistVO.class);
        Long id = 500L;

        when(therapistService.updateTherapist(any(TherapistVO.class), any(Long.class))).thenReturn(therapistVO);

        mockMvc.perform(
                put("/api/v1/therapist/" + id)
                .content(objectMapper.writeValueAsString(therapistVO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpectAll(
                status().isOk(),
                content().json(objectMapper.writeValueAsString(therapistVO))
        );
        verify(therapistService).updateTherapist(any(TherapistVO.class), any(Long.class));

    }

    @Test
    void deleteTherapistTest() throws Exception {
        Long id = 500L;

        mockMvc.perform(delete("/api/v1/therapist/" + id)
        ).andExpectAll(
                status().isNoContent()
        );
        verify(therapistService, times(1)).deleteTherapistById(id);
    }

    @Test
    void getTherapistByIdTest() throws Exception {

        Long id = 560L;
        TherapistVO therapistVO = mock(TherapistVO.class);

        when(therapistService.getTherapistById(id)).thenReturn(therapistVO);

        mockMvc.perform(get("/api/v1/therapist/" + id))
                .andExpectAll(
                        status().isOk(),
                        content().json(objectMapper.writeValueAsString(therapistVO))
                );

        verify(therapistService, times(1)).getTherapistById(id);

    }

    @Test
    void getAllTherapistsTest() throws Exception {

        TherapistVO therapistVO = mock(TherapistVO.class);
        TherapistVO therapistVO2 = mock(TherapistVO.class);
        TherapistVO therapistVO3 = mock(TherapistVO.class);

        List<TherapistVO> therapists = List.of(therapistVO, therapistVO2, therapistVO3);

        when(therapistService.getAllTherapists()).thenReturn(therapists);

        mockMvc.perform(get("/api/v1/therapist"))
                .andExpectAll(
                        status().isOk(),
                        content().json(objectMapper.writeValueAsString(therapists))
                );

        verify(therapistService, times(1)).getAllTherapists();


    }


}
