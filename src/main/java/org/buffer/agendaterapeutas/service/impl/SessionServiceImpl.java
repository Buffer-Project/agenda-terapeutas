package org.buffer.agendaterapeutas.service.impl;

import org.buffer.agendaterapeutas.exception.errors.PatientError;
import org.buffer.agendaterapeutas.exception.errors.SessionError;
import org.buffer.agendaterapeutas.exception.errors.TherapistError;
import org.buffer.agendaterapeutas.enums.SessionStatusEnum;
import org.buffer.agendaterapeutas.model.entity.Patient;
import org.buffer.agendaterapeutas.model.entity.Session;
import org.buffer.agendaterapeutas.model.entity.Therapist;
import org.buffer.agendaterapeutas.repository.IPatientRepository;
import org.buffer.agendaterapeutas.repository.ISessionRepository;
import org.buffer.agendaterapeutas.repository.ITherapistRepository;
import org.buffer.agendaterapeutas.service.ISessionService;
import org.buffer.agendaterapeutas.model.vo.SessionVO;
import org.springframework.stereotype.Service;
import org.buffer.agendaterapeutas.exception.SessionException;
import org.buffer.agendaterapeutas.exception.PatientException;
import org.buffer.agendaterapeutas.exception.TherapistException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SessionServiceImpl implements ISessionService {

    private final ISessionRepository sessionRepository;
    private final IPatientRepository patientRepository;
    private final ITherapistRepository therapistRepository;


    public SessionServiceImpl(ISessionRepository sessionRepository, IPatientRepository patientRepository, ITherapistRepository therapistRepository) {
        this.sessionRepository = sessionRepository;
        this.patientRepository = patientRepository;
        this.therapistRepository = therapistRepository;
    }


    @Override
    public SessionVO createSession(SessionVO sessionVO) {
        Therapist therapist = therapistRepository.findById(sessionVO.getTherapist().getId())
                .orElseThrow(() -> new TherapistException(TherapistError.NOT_FOUND, sessionVO.getTherapist().getId()));

        Patient patient = patientRepository.findById(sessionVO.getPatient().getId())
                .orElseThrow(() -> new PatientException(PatientError.NOT_FOUND, sessionVO.getPatient().getId()));

        Session session = new Session();
        session.setTherapist(therapist);
        session.setPatient(patient);
        session.setStartDateTime(sessionVO.getStartDateTime());
        session.setEndDateTime(sessionVO.getEndDateTime());
        session.setStatus(SessionStatusEnum.RESERVED);

        Session savedSession = sessionRepository.save(session);
        return new SessionVO(savedSession);

    }

    @Override
    public SessionVO getSessionById(Long id) {
        Optional<Session> session = sessionRepository.findById(id);
        if (session.isEmpty()) {
            throw new SessionException(SessionError.NOT_FOUND, id);
        }
        return new SessionVO(session.get());
    }

    @Override
    public SessionVO updateSession(SessionVO session, Long id) {

        if (id == null || session.getId() == null) {
            throw new SessionException(SessionError.MISSING_ID);
        }

        if (!session.getId().equals(id)) {
            throw new SessionException(SessionError.ID_CONFLICT);
        }

        if (!sessionRepository.existsById(id)) {
            throw new SessionException(SessionError.NOT_FOUND, id);
        }

        Session updatedSession = sessionRepository.save(new Session(session));
        return new SessionVO(updatedSession);
    }

    @Override
    public void deleteSession(Long id) {
        Optional<Session> session = sessionRepository.findById(id);
        if (session.isEmpty()) {
            throw new SessionException(SessionError.NOT_FOUND);
        }
        sessionRepository.deleteById(id);
    }

    @Override
    public void cancelSession(Long id) {
        Optional<Session> session = sessionRepository.findById(id);
        if (session.isEmpty()) {
            throw new SessionException(SessionError.NOT_FOUND);
        }
        if (session.get().getStartDateTime().isAfter(LocalDateTime.now())) {
            session.get().setStatus(SessionStatusEnum.CANCELLED);
            sessionRepository.save(session.get());
        } else {
            throw new SessionException(SessionError.CANNOT_CANCEL_PAST_SESSION);
        }

    }

    @Override
    public List<SessionVO> getAllSessions() {
        return sessionRepository.findAll().stream()
                .map(SessionVO::new)
                .toList();
    }

    public List<SessionVO> getSessionsByDateRange(LocalDateTime start, LocalDateTime end) {
        return sessionRepository.findByStartDateTimeBetween(start, end)
                .stream()
                .map(SessionVO::new)
                .toList();
    }

    @Override
    public List<SessionVO> getSessionsByTherapistId(Long therapistId) {
        return sessionRepository.findByTherapistId(therapistId)
                .stream()
                .map(SessionVO::new)
                .toList();
    }


}
