package org.buffer.agendaterapeutas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.buffer.agendaterapeutas.enums.SessionStatusEnum;
import org.buffer.agendaterapeutas.model.vo.PatientVO;
import org.buffer.agendaterapeutas.model.vo.SessionVO;
import org.buffer.agendaterapeutas.model.vo.TherapistVO;
import org.buffer.agendaterapeutas.service.ISessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SessionControllerTests {

    private MockMvc mockMvc;
    private final ISessionService sessionService = mock(ISessionService.class);
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // fuerza strings ISO-8601

        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        jacksonConverter.setObjectMapper(objectMapper);

        mockMvc = MockMvcBuilders
                .standaloneSetup(new SessionController(sessionService))
                .setMessageConverters(jacksonConverter)
                .build();
    }

    @Test
    public void testCreateSession() throws Exception {
        SessionVO body = new SessionVO();
        body.setStartDateTime(LocalDateTime.now().plusHours(1).withNano(0));
        body.setEndDateTime(body.getStartDateTime().plusHours(1));
        body.setStatus(SessionStatusEnum.RESERVED);
        TherapistVO t = new TherapistVO(); t.setId(1L); body.setTherapist(t);
        PatientVO p = new PatientVO(); p.setId(2L); body.setPatient(p);

        SessionVO created = new SessionVO();
        created.setIdSession(10L);
        created.setStartDateTime(body.getStartDateTime());
        created.setEndDateTime(body.getEndDateTime());
        created.setStatus(SessionStatusEnum.RESERVED);
        created.setTherapist(t);
        created.setPatient(p);

        when(sessionService.createSession(any(SessionVO.class))).thenReturn(created);

        mockMvc.perform(post("/api/v1/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(created)));

        verify(sessionService).createSession(any(SessionVO.class));
    }

    @Test
    public void testGetSessionById() throws Exception {
        SessionVO s = new SessionVO();
        s.setIdSession(5L);
        s.setStartDateTime(LocalDateTime.now().plusHours(2).withNano(0));
        s.setEndDateTime(s.getStartDateTime().plusHours(1));
        s.setStatus(SessionStatusEnum.RESERVED);
        TherapistVO t = new TherapistVO(); t.setId(1L); s.setTherapist(t);
        PatientVO p = new PatientVO(); p.setId(2L); s.setPatient(p);

        when(sessionService.getSessionById(5L)).thenReturn(s);

        mockMvc.perform(get("/api/v1/session/{id}", 5L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(s)));

        verify(sessionService).getSessionById(5L);
    }

    @Test
    public void testUpdateSession() throws Exception {
        Long id = 7L;

        SessionVO body = new SessionVO();
        body.setIdSession(id);
        body.setStartDateTime(LocalDateTime.now().plusHours(3).withNano(0));
        body.setEndDateTime(body.getStartDateTime().plusHours(1));
        body.setStatus(SessionStatusEnum.RESERVED);
        TherapistVO therapist = new TherapistVO(); therapist.setId(1L); body.setTherapist(therapist);
        PatientVO patient = new PatientVO(); patient.setId(2L); body.setPatient(patient);

        SessionVO updated = new SessionVO();
        updated.setIdSession(id);
        updated.setStartDateTime(body.getStartDateTime());
        updated.setEndDateTime(body.getEndDateTime());
        updated.setStatus(SessionStatusEnum.RESERVED);
        updated.setTherapist(therapist);
        updated.setPatient(patient);

        when(sessionService.updateSession(any(SessionVO.class), eq(id))).thenReturn(updated);

        mockMvc.perform(put("/api/v1/session/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updated)));

        verify(sessionService).updateSession(any(SessionVO.class), eq(id));
    }

    @Test
    public void testDeleteSession() throws Exception {
        doNothing().when(sessionService).deleteSession(3L);

        mockMvc.perform(delete("/api/v1/session/{id}", 3L))
                .andExpect(status().isOk());

        verify(sessionService).deleteSession(3L);
    }

    @Test
    public void testCancelSession() throws Exception {
        doNothing().when(sessionService).cancelSession(4L);

        mockMvc.perform(delete("/api/v1/session/cancelReservation/{id}", 4L))
                .andExpect(status().isOk());

        verify(sessionService).cancelSession(4L);
    }

    @Test
    public void testGetAllSessions() throws Exception {
        SessionVO s1 = new SessionVO();
        s1.setIdSession(1L);
        s1.setStartDateTime(LocalDateTime.now().plusHours(1).withNano(0));
        s1.setEndDateTime(s1.getStartDateTime().plusHours(1));
        s1.setStatus(SessionStatusEnum.RESERVED);
        s1.setTherapist(new TherapistVO()); s1.getTherapist().setId(1L);
        s1.setPatient(new PatientVO()); s1.getPatient().setId(2L);

        SessionVO s2 = new SessionVO();
        s2.setIdSession(2L);
        s2.setStartDateTime(LocalDateTime.now().plusHours(2).withNano(0));
        s2.setEndDateTime(s2.getStartDateTime().plusHours(1));
        s2.setStatus(SessionStatusEnum.RESERVED);
        s2.setTherapist(new TherapistVO()); s2.getTherapist().setId(1L);
        s2.setPatient(new PatientVO()); s2.getPatient().setId(3L);

        when(sessionService.getAllSessions()).thenReturn(List.of(s1, s2));

        mockMvc.perform(get("/api/v1/session/findAll")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(s1, s2))));

        verify(sessionService).getAllSessions();
    }

    @Test
    public void testGetSessionsByDateRange() throws Exception {
        LocalDateTime start = LocalDateTime.now().minusDays(1).withNano(0);
        LocalDateTime end = LocalDateTime.now().plusDays(1).withNano(0);

        SessionVO s = new SessionVO();
        s.setIdSession(11L);
        s.setStartDateTime(start.plusHours(1));
        s.setEndDateTime(start.plusHours(2));
        s.setStatus(SessionStatusEnum.RESERVED);
        s.setTherapist(new TherapistVO()); s.getTherapist().setId(1L);
        s.setPatient(new PatientVO()); s.getPatient().setId(2L);

        when(sessionService.getSessionsByDateRange(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(s));

        mockMvc.perform(get("/api/v1/session/range")
                        .param("startDate", start.toString())
                        .param("endDate", end.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(s))));

        verify(sessionService).getSessionsByDateRange(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    public void testGetSessionsByTherapist() throws Exception {
        SessionVO s = new SessionVO();
        s.setIdSession(100L);
        s.setStartDateTime(LocalDateTime.now().plusHours(1).withNano(0));
        s.setEndDateTime(s.getStartDateTime().plusHours(1));
        s.setStatus(SessionStatusEnum.RESERVED);
        TherapistVO t = new TherapistVO(); t.setId(5L); s.setTherapist(t);
        PatientVO p = new PatientVO(); p.setId(2L); s.setPatient(p);

        when(sessionService.getSessionsByTherapistId(5L)).thenReturn(List.of(s));

        mockMvc.perform(get("/api/v1/session/therapist/{therapistId}", 5L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(s))));

        verify(sessionService).getSessionsByTherapistId(5L);
    }

}
