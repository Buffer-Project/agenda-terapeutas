package org.buffer.agendaterapeutas.controller;

import org.buffer.agendaterapeutas.exception.SchedulerException;
import org.buffer.agendaterapeutas.model.entity.User;
import org.buffer.agendaterapeutas.service.IUserService;
import org.buffer.agendaterapeutas.model.vo.UserVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserVO> createUser(@RequestBody UserVO user) {
        try {
            UserVO response = userService.createUser(user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<UserVO> updateUser(UserVO user) {
        // TODO
        return null;
    }

    @DeleteMapping
    public void deleteUser(UserVO user) {
        // TODO

    }

    @GetMapping("/{userId}")  //api/v1/user/358
    public ResponseEntity<UserVO> getUserById(@PathVariable Long userId) {
        try{
            UserVO response = userService.getUserById(userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }
}
