package org.buffer.agendaterapeutas.service.impl;


import org.buffer.agendaterapeutas.exception.UserException;
import org.buffer.agendaterapeutas.exception.errors.UserError;
import org.buffer.agendaterapeutas.model.entity.User;
import org.buffer.agendaterapeutas.model.vo.UserVO;
import org.buffer.agendaterapeutas.repository.IUserRepository;
import org.buffer.agendaterapeutas.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

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
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(UserError.NOT_FOUND, id));

        return new UserVO(user);
    }


    @Override
    public UserVO updateUser(UserVO user, Long id) {

        if (id == null || user.getId() == null) {
            throw new UserException(UserError.MISSING_ID);
        }

        if (!userRepository.existsById(id)) {
            throw new UserException(UserError.NOT_FOUND, id);
        }

        if (!user.getId().equals(id)) {
            throw new UserException(UserError.ID_CONFLICT);
        }

        User updatedUser = userRepository.save(new User(user));
        return new UserVO(updatedUser);

    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(UserError.NOT_FOUND, id));

        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public List<UserVO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserVO::new)
                .toList();
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

}
