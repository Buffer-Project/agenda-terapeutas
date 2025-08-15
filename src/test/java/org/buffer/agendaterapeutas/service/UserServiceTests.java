package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.exception.UserException;
import org.buffer.agendaterapeutas.exception.errors.UserError;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {


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

        assertEquals(1L, result.getId());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createUserWhenEmailAlreadyRegisteredTest() {
        UserVO userVO = getMockUserVO();

        when(userRepository.existsByEmail("test@test.com")).thenReturn(true);

        UserException result = assertThrows(UserException.class, () -> {
            userService.createUser(userVO);
        });

        assertEquals(result.getCode(), UserError.EMAIL_ALREADY_EXISTS.getCode());

        verify(userRepository, times(1)).existsByEmail("test@test.com");

    }

    @Test
    void getUserByIdTest() {
        Long id = 1L;
        UserVO userVO = getMockUserVO();
        User userEntity = new User(userVO);

        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));

        UserVO result = userService.getUserById(id);

        assert (result.getEmail().equals("test@test.com"));

        verify(userRepository).findById(id);
    }

    @Test
    void getUserByIdWhenUserNotFoundTest() {
        Long id = 21L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        UserException result = assertThrows(UserException.class, () -> {
            userService.getUserById(id);
        });

        assertEquals(result.getCode(), UserError.NOT_FOUND.getCode());

        verify(userRepository, times(1)).findById(id);

    }

    @Test
    void updateUserTest() {
        Long id = 25L;
        UserVO userVO = getMockUserVO();
        userVO.setId(id);
        User updatedUserEntity = new User(userVO);

        when(userRepository.existsById(id)).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(updatedUserEntity);

        UserVO result = userService.updateUser(userVO, id);

        assertNotNull(result);
        assertEquals(id, result.getId());

    }

    @Test
    void updateUserWhenProvidedIdIsNullTest() {
        Long id = null;

        UserVO userVO = getMockUserVO();

        UserException result = assertThrows(UserException.class, () -> {
            userService.updateUser(userVO, id);
        });

        assertEquals(UserError.MISSING_ID.getCode(), result.getCode());
        assertEquals(UserException.class, result.getClass());


    }

    @Test
    void updateUserWhenUserIdIsNullTest() {
        Long id = 5L;
        UserVO userVO = getMockUserVO();
        userVO.setId(null);

        UserException result = assertThrows(UserException.class, () -> {
            userService.updateUser(userVO, id);
        });

        assertEquals(UserError.MISSING_ID.getCode(), result.getCode());
        assertEquals(UserException.class, result.getClass());

    }

    @Test
    void updateUserWhenUserDoesNotExistTest() {

        Long id = 400L;
        UserVO userVO = getMockUserVO();

        when(userRepository.existsById(id)).thenReturn(false);

        UserException result = assertThrows(UserException.class, () -> {
            userService.updateUser(userVO, id);
        });

        assertEquals(UserError.NOT_FOUND.getCode(), result.getCode());
        assertEquals(UserException.class, result.getClass());

    }

    @Test
    void updateUserWhenProvidedIdDoesNotMatchUserIdTest() {


        Long id = 400L;
        UserVO userVO = getMockUserVO();

        when(userRepository.existsById(id)).thenReturn(true);

        UserException result = assertThrows(UserException.class, () -> {
            userService.updateUser(userVO, id);
        });

        assertEquals(UserError.ID_CONFLICT.getCode(), result.getCode());
        assertEquals(UserException.class, result.getClass());

    }


    @Test
    void deleteUserByIdTest() {
        Long id = 15L;
        UserVO userVO = getMockUserVO();
        userVO.setId(id);
        User user = new User(userVO);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userService.deleteUser(15L);

        assertFalse(user.isActive());
        assertEquals(15L, user.getId());

    }

    @Test
    void deleteUserWhenUserNotFoundTest() {
        Long id = 10L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        UserException result = assertThrows(UserException.class, () -> {
            userService.deleteUser(10L);
        });

        assertEquals(UserError.NOT_FOUND.getCode(), result.getCode());
        assertEquals(UserException.class, result.getClass());

    }

    @Test
    void getAllUsersTest() {

        List<User> users = new ArrayList<>();
        User user = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();
        users.add(user);
        users.add(user2);
        users.add(user3);
        users.add(user4);


        when(userRepository.findAll()).thenReturn(users);

        List<UserVO> result = userService.getAllUsers();

        assertEquals(users.size(), result.size());

        verify(userRepository, times(1)).findAll();

    }

    @Test
    void existsByIdTest() {
        Long id = 1L;
        when(userRepository.existsById(id)).thenReturn(true);

        boolean result = userService.existsById(id);

        assertTrue(result);

        verify(userRepository, times(1)).existsById(id);
    }


}
