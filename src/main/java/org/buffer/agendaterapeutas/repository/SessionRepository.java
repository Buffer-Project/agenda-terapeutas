package org.buffer.agendaterapeutas.repository;

import org.buffer.agendaterapeutas.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long>{
    boolean existsByIdSession(Long idSession);

}


