package org.buffer.agendaterapeutas.controller;

import org.buffer.agendaterapeutas.service.IUserService;
import org.buffer.agendaterapeutas.model.vo.UserVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/{id}")
    public ResponseEntity<UserVO> updateUser(@RequestBody UserVO user, @PathVariable Long id) {
        UserVO response = userService.updateUser(user, id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserVO> getUserById(@PathVariable Long id) {
        UserVO response = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserVO>> getAllUsers() {
        List<UserVO> response = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
