package org.buffer.agendaterapeutas.service.impl;


import org.buffer.agendaterapeutas.exception.TherapistException;
import org.buffer.agendaterapeutas.exception.UserException;
import org.buffer.agendaterapeutas.exception.errors.TherapistError;
import org.buffer.agendaterapeutas.exception.errors.UserError;
import org.buffer.agendaterapeutas.model.bo.UserBO;
import org.buffer.agendaterapeutas.model.entity.Therapist;
import org.buffer.agendaterapeutas.model.entity.User;
import org.buffer.agendaterapeutas.model.vo.TherapistVO;
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
    public UserVO createUser(UserVO userVO) {
        if (userRepository.existsByEmail(userVO.getEmail())) {
            throw new UserException(UserError.EMAIL_ALREADY_EXISTS);
        }
        userVO.setId(null);
        userVO.setActive(true);
        User savedUser = userRepository.save(new User(userVO));
        return new UserVO(savedUser);

    }

    @Override
    public UserVO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserException(UserError.NOT_FOUND);
        }
        return new UserVO(user.get());
    }

    @Override
    public UserVO updateUser(UserVO user, Long id) {

        if (id == null || user.getId() == null) {
            throw new TherapistException(TherapistError.MISSING_ID);
        }

        if (!userRepository.existsById(id)) {
            throw new TherapistException(TherapistError.NOT_FOUND, id);
        }

        if (!user.getId().equals(id)) {
            throw new TherapistException(TherapistError.ID_CONFLICT);
        }

        User updatedUser = userRepository.save(new User(user));
        return new UserVO(updatedUser);

    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserException(UserError.NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

}
