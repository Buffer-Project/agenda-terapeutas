package org.buffer.agendaterapeutas.service.impl;


import org.buffer.agendaterapeutas.exception.UserException;
import org.buffer.agendaterapeutas.exception.errors.UserError;
import org.buffer.agendaterapeutas.model.bo.UserBO;
import org.buffer.agendaterapeutas.model.entity.User;
import org.buffer.agendaterapeutas.model.vo.UserVO;
import org.buffer.agendaterapeutas.repository.IUserRepository;
import org.buffer.agendaterapeutas.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserVO createUser(UserVO user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserException(UserError.EMAIL_ALREADY_EXISTS);
        }
        User savedUser = userRepository.save(new User(new UserBO(user)));
        UserBO userBO = new UserBO(savedUser);
        return new UserVO(userBO);

    }

    @Override
    public UserVO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserException(UserError.NOT_FOUND);
        }
        UserBO userBO = new UserBO(user.get());
        return new UserVO(userBO);
    }


}
