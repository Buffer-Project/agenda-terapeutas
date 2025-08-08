package org.buffer.agendaterapeutas.repository;


import org.buffer.agendaterapeutas.model.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ISessionRepository extends JpaRepository<Session, Long>, JpaSpecificationExecutor<Session> {
    List<Session> findByTherapistId(Long therapistId);
}


