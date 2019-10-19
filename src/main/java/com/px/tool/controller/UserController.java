package com.px.tool.controller;

import com.px.tool.model.User;
import com.px.tool.model.request.UserRequest;
import com.px.tool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> findUsers() {
        return userService.findUsers();
    }

    @PostMapping
    public User createUser(@RequestBody UserRequest user) {
        return this.userService.create(user);
    }

    @DeleteMapping("/{id}")
    public Long deleteUser(@PathVariable Long id) {
        return this.userService.delete(id);
    }
}
