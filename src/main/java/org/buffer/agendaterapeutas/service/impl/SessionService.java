package org.buffer.agendaterapeutas.service.impl;

import org.buffer.agendaterapeutas.enums.SessionStatusEnum;
import org.buffer.agendaterapeutas.model.Patient;
import org.buffer.agendaterapeutas.model.Session;
import org.buffer.agendaterapeutas.model.Therapist;
import org.buffer.agendaterapeutas.repository.PatientRepository;
import org.buffer.agendaterapeutas.repository.SessionRepository;
import org.buffer.agendaterapeutas.repository.TherapistRepository;
import org.buffer.agendaterapeutas.service.ISessionService;
import org.buffer.agendaterapeutas.vo.SessionVO;
import org.springframework.stereotype.Service;
import org.buffer.agendaterapeutas.exception.SessionNotFoundException;
import org.buffer.agendaterapeutas.exception.PatientNotFoundException;
import org.buffer.agendaterapeutas.exception.TherapistNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SessionService implements ISessionService {

    private final SessionRepository sessionRepository;
    private final PatientRepository patientRepository;
    private final TherapistRepository therapistRepository;

    public SessionService(SessionRepository sessionRepository, PatientRepository patientRepository, TherapistRepository therapistRepository) {
        this.sessionRepository = sessionRepository;
        this.patientRepository = patientRepository;
        this.therapistRepository = therapistRepository;
    }

    @Override
    public Session createSession(SessionVO sessionVO) {
        Therapist therapist = therapistRepository.findById(sessionVO.getTherapistId())
                .orElseThrow(TherapistNotFoundException::new);

        Patient patient = patientRepository.findById(sessionVO.getPatientId())
                .orElseThrow(PatientNotFoundException::new);

        Session session = new Session();
        session.setTherapist(therapist);
        session.setPatient(patient);
        session.setStartDateTime(LocalDateTime.parse(sessionVO.getStartDateTime()));
        session.setEndDateTime(LocalDateTime.parse(sessionVO.getEndDateTime()));
        session.setStatus(SessionStatusEnum.RESERVED); //Aca entiendo que deberia ir siempre en pending al crearlo

        return sessionRepository.save(session);
    }

    @Override
    public Session getSessionById(Long id) {
        Optional<Session> session = sessionRepository.findById(id);
        return session.orElseThrow(SessionNotFoundException::new);
    }

    @Override
    public Session updateSession(Session session) {

        if (session.getIdSession() == null || !sessionRepository.existsById(session.getIdSession())) {
            throw new SessionNotFoundException();
        }
        return sessionRepository.save(session);
    }

    @Override
    public void deleteSession(Long id) {
        Optional<Session> session = sessionRepository.findById(id);
        if (session.isEmpty()) {
            throw new SessionNotFoundException();
        }
        sessionRepository.deleteById(id);
    }

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

}
