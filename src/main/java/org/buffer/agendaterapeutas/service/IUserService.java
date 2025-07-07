package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.model.User;

public interface IUserService {
    User createUser(User user) throws Exception;
    User getUserById(Long id) ;
}
