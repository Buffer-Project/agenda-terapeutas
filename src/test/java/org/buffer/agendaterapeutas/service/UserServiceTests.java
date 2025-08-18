package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.model.entity.User;
import org.buffer.agendaterapeutas.model.vo.UserVO;
import org.buffer.agendaterapeutas.repository.IUserRepository;
import org.buffer.agendaterapeutas.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {



    @Mock
    IUserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    private UserVO getMockUserVO() {
        UserVO userVO = new UserVO();
        userVO.setId(1L);
        userVO.setEmail("test@test.com");
        userVO.setActive(true);
        userVO.setUsername("testUser");
        userVO.setFirstName("Test");
        userVO.setLastName("User");
        userVO.setPassword("testPassword");
        userVO.setPhone("1234567890");
        userVO.setGender("M");
        userVO.setActive(true);
        return userVO;

    }

    @BeforeEach
    void setup() {

    }

    @Test
    void createUserTest() {
        UserVO userVO = getMockUserVO();
        User userEntity = new User(userVO);

        when(userRepository.save(any(User.class))).thenReturn(userEntity);

        UserVO result = userService.createUser(userVO);

        assert (result.getId().equals(1L));
        assert (result.getEmail().equals("test@test.com"));
        assert (result.getUsername().equals("testUser"));
        assert (result.getFirstName().equals("Test"));
        assert (result.getLastName().equals("User"));
        assert (result.getPassword().equals("testPassword"));
        assert (result.getPhone().equals("1234567890"));
        assert (result.getGender().equals("M"));
        assert (result.isActive());
        verify(userRepository, times(1)).save(any(User.class));


    }

    @Test
    void getUserById() {
        Long id = 1L;
        UserVO userVO = getMockUserVO();
        User userEntity = new User(userVO);

        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));

        UserVO result = userService.getUserById(id);

        assert (result.getEmail().equals("test@test.com"));

        verify(userRepository).findById(id);
    }


}
