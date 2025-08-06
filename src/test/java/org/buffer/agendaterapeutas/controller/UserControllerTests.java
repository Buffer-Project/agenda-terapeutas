package org.buffer.agendaterapeutas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.buffer.agendaterapeutas.model.vo.UserVO;
import org.buffer.agendaterapeutas.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTests {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    private final UserServiceImpl userService = mock(UserServiceImpl.class);

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
    }

    @Test
    void createUserTest() throws Exception {

        UserVO userVO = mock(UserVO.class);

        when(userService.createUser(any())).thenReturn(userVO);


        mockMvc.perform(
                post("/api/v1/user")
                        .content(objectMapper.writeValueAsString(userVO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isCreated(),
                content().json(objectMapper.writeValueAsString(userVO))
        );

        verify(userService).createUser(any(UserVO.class));

    }

    @Test
    void getUserByIdTest() throws Exception {
        Long id = 23L;

        UserVO userVO = mock(UserVO.class);

        when(userService.getUserById(id)).thenReturn(userVO);

        mockMvc.perform(
                get("/api/v1/user/" + id)
        ).andExpectAll(
                status().isOk(),
                content().json(objectMapper.writeValueAsString(userVO)));

        verify(userService).getUserById(id);
    }

    @Test
    void updateUserTest() {
      //TODO: implement
    }


}
