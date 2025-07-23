package org.buffer.agendaterapeutas.repository;

import org.buffer.agendaterapeutas.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    List<User> findByActiveTrue();
}
