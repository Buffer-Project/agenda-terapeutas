package org.buffer.agendaterapeutas.mapper.impl;

import org.buffer.agendaterapeutas.BusinessObject.SessionBO;
import org.buffer.agendaterapeutas.mapper.ISessionMapper;
import org.buffer.agendaterapeutas.model.Session;
import org.buffer.agendaterapeutas.vo.SessionVO;

public class SessionMapperImpl implements ISessionMapper {


    @Override
    public SessionBO toBO(Session entity) {
        SessionBO sessionBO = new SessionBO();
        sessionBO.setIdSession(entity.getIdSession());
        sessionBO.setTherapist(entity.getTherapist());
        sessionBO.setPatient(entity.getPatient());
        sessionBO.setStartDateTime(entity.getStartDateTime());
        sessionBO.setEndDateTime(entity.getEndDateTime());
        sessionBO.setStatus(entity.getStatus());
        return sessionBO;
    }

    @Override
    public Session toEntity(SessionBO sessionBO) {
        Session session = new Session();
        session.setIdSession(sessionBO.getIdSession());
        session.setTherapist(sessionBO.getTherapist());
        session.setPatient(sessionBO.getPatient());
        session.setStartDateTime(sessionBO.getStartDateTime());
        session.setEndDateTime(sessionBO.getEndDateTime());
        session.setStatus(sessionBO.getStatus());
        return session;
    }

    @Override
    public SessionVO toVO(SessionBO sessionBO) {
        SessionVO sessionVO = new SessionVO();
        sessionBO.setIdSession(sessionBO.getIdSession());
        sessionBO.setTherapist(sessionBO.getTherapist());
        sessionBO.setPatient(sessionBO.getPatient());
        sessionBO.setStartDateTime(sessionBO.getStartDateTime());
        sessionBO.setEndDateTime(sessionBO.getEndDateTime());
        sessionBO.setStatus(sessionBO.getStatus());
        return sessionVO;
    }


}
