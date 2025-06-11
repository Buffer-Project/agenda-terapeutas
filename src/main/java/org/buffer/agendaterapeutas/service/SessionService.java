package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.model.Patient;
import org.buffer.agendaterapeutas.model.Session;
import org.buffer.agendaterapeutas.model.Therapist;
import org.buffer.agendaterapeutas.repository.PatientRepository;
import org.buffer.agendaterapeutas.repository.SessionRepository;
import org.buffer.agendaterapeutas.repository.TherapistRepository;
import org.buffer.agendaterapeutas.vo.SessionVO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final PatientRepository patientRepository;
    private final TherapistRepository therapistRepository;

    public SessionService(SessionRepository sessionRepository, PatientRepository patientRepository, TherapistRepository therapistRepository) {
        this.sessionRepository = sessionRepository;
        this.patientRepository = patientRepository;
        this.therapistRepository = therapistRepository;
    }

    public Session createSession(SessionVO sessionVO) throws Exception {
        Therapist therapist = therapistRepository.findById(sessionVO.getTherapistId())
                .orElseThrow(() -> new Exception("Therapist not found with id: " + sessionVO.getTherapistId()));

        Patient patient = patientRepository.findById(sessionVO.getPatientId())
                .orElseThrow(() -> new Exception("Patient not found with id: " + sessionVO.getPatientId()));

        Session session = new Session();
        session.setTherapist(therapist);
        session.setPatient(patient);
        session.setStartDateTime(LocalDateTime.parse(sessionVO.getStartDateTime()));
        session.setEndDateTime(LocalDateTime.parse(sessionVO.getEndDateTime()));
        session.setStatus(sessionVO.getStatus());

        return sessionRepository.save(session);
    }

    public Session getSessionById(Long id) throws Exception {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new Exception("Session not found with id: " + id));
    }

    public Session updateSession(Session session) throws Exception {
        Long id = session.getIdSession();
        if (id == null || !sessionRepository.existsById(id)) {
            throw new Exception("Session not found with id: " + id);
        }
        return sessionRepository.save(session);
    }

    public void deleteSession(Long id) throws Exception {
        if (!sessionRepository.existsById(id)) {
            throw new Exception("Session not found with id: " + id);
        }
        sessionRepository.deleteById(id);
    }

    public List<Session> getAllSessions() {
        return sessionRepository.findAll(); 
    }
}
