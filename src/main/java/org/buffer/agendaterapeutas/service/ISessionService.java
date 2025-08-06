package org.buffer.agendaterapeutas.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.buffer.agendaterapeutas.model.vo.SessionVO;

import java.time.LocalDateTime;
import java.util.List;

public interface ISessionService {
    SessionVO createSession(SessionVO sessionVO);

    SessionVO getSessionById(Long id);

    List<SessionVO> getAllSessions();

    List<SessionVO> getSessionsByDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime);

    SessionVO updateSession(SessionVO session,Long id);

    void deleteSession(Long id);

    void cancelSession(Long id, JsonPatch patch) throws JsonPatchException, JsonProcessingException;

    List<SessionVO> getSessionsByTherapistId(Long therapistId);



}

