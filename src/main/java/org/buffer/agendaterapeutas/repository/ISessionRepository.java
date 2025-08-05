package org.buffer.agendaterapeutas.repository;

import org.buffer.agendaterapeutas.model.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ISessionRepository extends JpaRepository<Session, Long>{
    List<Session> findByStartDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Session> findByTherapistId(Long therapistId);


}


