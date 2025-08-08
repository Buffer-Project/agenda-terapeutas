package org.buffer.agendaterapeutas.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.buffer.agendaterapeutas.enums.SessionStatusEnum;
import org.buffer.agendaterapeutas.exception.PatientException;
import org.buffer.agendaterapeutas.exception.SessionException;
import org.buffer.agendaterapeutas.exception.TherapistException;
import org.buffer.agendaterapeutas.exception.UserException;
import org.buffer.agendaterapeutas.exception.errors.PatientError;
import org.buffer.agendaterapeutas.exception.errors.SessionError;
import org.buffer.agendaterapeutas.exception.errors.TherapistError;
import org.buffer.agendaterapeutas.exception.errors.UserError;
import org.buffer.agendaterapeutas.model.entity.Patient;
import org.buffer.agendaterapeutas.model.entity.Session;
import org.buffer.agendaterapeutas.model.entity.Therapist;
import org.buffer.agendaterapeutas.model.entity.User;
import org.buffer.agendaterapeutas.model.vo.PatientVO;
import org.buffer.agendaterapeutas.model.vo.SessionVO;
import org.buffer.agendaterapeutas.model.vo.TherapistVO;
import org.buffer.agendaterapeutas.model.vo.UserVO;
import org.buffer.agendaterapeutas.repository.ISessionRepository;
import org.buffer.agendaterapeutas.service.IPatientService;
import org.buffer.agendaterapeutas.service.ISessionService;
import org.buffer.agendaterapeutas.service.ITherapistService;
import org.buffer.agendaterapeutas.service.IUserService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.buffer.agendaterapeutas.repository.specifications.SessionSpecifications.getSessionSpec;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class SessionServiceImpl implements ISessionService {


    private final ISessionRepository sessionRepository;
    private final IPatientService patientService;
    private final IUserService userService;
    private final ITherapistService therapistService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SessionServiceImpl(ISessionRepository sessionRepository, IPatientService patientService, IUserService userService, ITherapistService therapistService) {
        this.sessionRepository = sessionRepository;
        this.patientService = patientService;
        this.userService = userService;
        this.therapistService = therapistService;
    }


    @Override
    public SessionVO createSession(SessionVO sessionVO) {
        Session session = new Session();
        Long userId = sessionVO.getTherapist().getUser().getId();
        UserVO user = userService.getUserById(userId);
        if (user == null) {
            throw new UserException(UserError.NOT_FOUND, userId);
        }

        Long therapistId = sessionVO.getTherapist().getId();
        TherapistVO therapist = therapistService.getTherapistById(therapistId);
        if (therapist == null) {
            throw new TherapistException(TherapistError.NOT_FOUND, sessionVO.getTherapist().getId());
        }
        session.getTherapist().setUser(new User(user));
        session.setTherapist(new Therapist(therapist));

        Long patientId = sessionVO.getPatient().getId();
        PatientVO patient = patientService.getPatientById(patientId);
        if (patient == null) {
            throw new PatientException(PatientError.NOT_FOUND, sessionVO.getPatient().getId());
        }
        session.setPatient(new Patient(patient));

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
    public void updateSessionValue(Long id, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        Optional<Session> session = sessionRepository.findById(id);
        if (session.isEmpty()) {
            throw new SessionException(SessionError.NOT_FOUND);
        }
        if (!session.get().getStartDateTime().isAfter(LocalDateTime.now()))
            throw new SessionException(SessionError.CANNOT_CANCEL_PAST_SESSION);

        JsonNode patched = patch.apply(objectMapper.convertValue(session, JsonNode.class));
        Session updatedSession = objectMapper.treeToValue(patched, Session.class);
        sessionRepository.save(updatedSession);

    }

    @Override
    public List<SessionVO> getAllSessions(Map<String, String> params) {
        if (params.isEmpty()) {
            return sessionRepository.findAll().stream()
                    .map(SessionVO::new)
                    .toList();
        }
        return sessionRepository.findAll(getSpecifications(params)).stream()
                .map(SessionVO::new)
                .toList();
    }

    private Specification<Session> getSpecifications(Map<String, String> params) {
        if (params.isEmpty()) return null;
        String key = params.keySet().stream().findFirst().get();
        Specification<Session> spec = where(getSessionSpec(key, params.get(key)));
        params.remove(key);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            spec.and(getSessionSpec(entry.getKey(), entry.getValue()));
        }
        return spec;
    }

    @Override
    public List<SessionVO> getSessionsByTherapistId(Long therapistId) {
        return sessionRepository.findByTherapistId(therapistId)
                .stream()
                .map(SessionVO::new)
                .toList();
    }


}
