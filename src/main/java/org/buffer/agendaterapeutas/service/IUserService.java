package org.buffer.agendaterapeutas.service;


import org.buffer.agendaterapeutas.model.vo.UserVO;

public interface IUserService {
    UserVO createUser(UserVO user);
    UserVO getUserById(Long id) ;
}
