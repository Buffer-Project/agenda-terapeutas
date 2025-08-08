package org.buffer.agendaterapeutas.service;


import org.buffer.agendaterapeutas.model.vo.UserVO;

import java.util.List;

public interface IUserService {
    UserVO createUser(UserVO user);

    UserVO getUserById(Long id);

    UserVO updateUser(UserVO user, Long id);

    void deleteUser(Long id);

    List<UserVO> getAllUsers();
}
