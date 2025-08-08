package org.buffer.agendaterapeutas.controller;

import org.buffer.agendaterapeutas.service.IUserService;
import org.buffer.agendaterapeutas.model.vo.UserVO;
import org.springframework.http.HttpStatus;
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
        UserVO response = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserVO> updateUser(@RequestBody UserVO user, @PathVariable Long userId) {
        UserVO response = userService.updateUser(user, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserVO> getUserById(@PathVariable Long userId) {
        UserVO response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }
}
