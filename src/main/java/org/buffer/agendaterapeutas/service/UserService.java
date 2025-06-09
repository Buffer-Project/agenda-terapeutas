package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.model.User;
import org.buffer.agendaterapeutas.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) throws Exception {
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("AAAA");
        }
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow();
    }



}
