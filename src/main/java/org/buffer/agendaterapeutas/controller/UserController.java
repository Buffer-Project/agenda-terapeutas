package org.buffer.agendaterapeutas.controller;

import org.buffer.agendaterapeutas.model.User;
import org.buffer.agendaterapeutas.service.IUserService;
import org.buffer.agendaterapeutas.vo.UserVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user) throws Exception {
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(User user) throws Exception {
        throw new Exception();
    }

    @DeleteMapping
    public void deleteUser(User user) {}

    @GetMapping("/{userId}")  //api/v1/user/358
    public UserVO getUserById(@PathVariable Long userId) {
       return new UserVO(userService.getUserById(userId));
    }
}
