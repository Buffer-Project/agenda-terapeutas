package org.buffer.agendaterapeutas.service.impl;

import org.buffer.agendaterapeutas.exception.SchedulerException;
import org.buffer.agendaterapeutas.model.bo.SessionBO;
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
import org.buffer.agendaterapeutas.exception.SessionNotFoundException;
import org.buffer.agendaterapeutas.exception.PatientNotFoundException;
import org.buffer.agendaterapeutas.exception.TherapistNotFoundException;

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
                .orElseThrow(TherapistNotFoundException::new);

        Patient patient = patientRepository.findById(sessionVO.getPatient().getId())
                .orElseThrow(PatientNotFoundException::new);

        Session session = new Session();
        session.setTherapist(therapist);
        session.setPatient(patient);
        session.setStartDateTime(sessionVO.getStartDateTime());
        session.setEndDateTime(sessionVO.getEndDateTime());
        session.setStatus(SessionStatusEnum.RESERVED); //Aca entiendo que deberia ir siempre en pending al crearlo

        Session savedSession = sessionRepository.save(session);
        SessionBO sessionBO = new SessionBO(savedSession);
        return new SessionVO(sessionBO);

    }

    @Override
    public SessionVO getSessionById(Long id) {
        Optional<Session> session = sessionRepository.findById(id);
        if (session.isEmpty()) {
            throw new SessionNotFoundException();
        }
        SessionBO sessionBO = new SessionBO(session.get());
        return new SessionVO(sessionBO);
    }

    @Override
    public SessionVO updateSession(SessionVO session, Long id) {

        if (id == null) {
            throw new SessionNotFoundException("El ID de la sesión no puede ser null");
        }

        if (session.getIdSession() == null) {
            throw new SessionNotFoundException("El ID de la sesión en el objeto no puede ser null");
        }

        if (!session.getIdSession().equals(id)) {
            throw new SessionNotFoundException("El ID de la sesión no coincide con el ID de la URL");
        }

        if (!sessionRepository.existsById(id)) {
            throw new SessionNotFoundException("La sesión con ID " + id + " no existe");
        }

        SessionBO sessionBO = new SessionBO(session);
        Session updatedSession = sessionRepository.save(new Session(sessionBO));
        return new SessionVO(new SessionBO(updatedSession));
    }

    @Override
    public void deleteSession(Long id) {
        Optional<Session> session = sessionRepository.findById(id);
        if (session.isEmpty()) {
            throw new SessionNotFoundException();
        }
        sessionRepository.deleteById(id);
    }

    @Override
    public void cancelSession(Long id) {
        Optional<Session> session = sessionRepository.findById(id);
        if (session.isEmpty()) {
            throw new SessionNotFoundException();
        }
        if (session.get().getStartDateTime().isAfter(LocalDateTime.now())) {
            session.get().setStatus(SessionStatusEnum.CANCELED);
            sessionRepository.save(session.get());
        } else {
            throw new SchedulerException("No se puede cancelar una sesion pasada"); //TODO: Mejorar
        }

    }

    @Override
    public List<SessionVO> getAllSessions() {
        return sessionRepository.findAll().stream()
                .map(s -> new SessionVO(new SessionBO(s)))
                .toList();
    }

    public List<SessionVO> getSessionsByDateRange(LocalDateTime start, LocalDateTime end) {
        return sessionRepository.findByStartDateTimeBetween(start, end)
                .stream()
                .map(s -> new SessionVO(new SessionBO(s)))
                .toList();
    }

    @Override
    public List<SessionVO> getSessionsByTherapistId(Long therapistId) {
        return sessionRepository.findByTherapistId(therapistId)
                .stream()
                .map(s -> new SessionVO(new SessionBO(s)))
                .toList();
    }


}
