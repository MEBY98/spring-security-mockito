package com.example.meby98.TestWithMockito.controllers;

import java.util.List;

import javax.validation.Valid;

import com.example.meby98.TestWithMockito.dto.UserRequest;
import com.example.meby98.TestWithMockito.models.User;
import com.example.meby98.TestWithMockito.service.UserService;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private static final String USER_URL = "/users";
    private static final String REGISTRATION_URL = "/register";
    private final UserService userService;

    @GetMapping(USER_URL)
    public List<User> getAllUsers() {
        return this.userService.findAll();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(USER_URL + "/{id}")
    public User getUserById(@PathVariable(name = "id") Long id) {
        return this.userService.findById(id);
    }

    @PostMapping(REGISTRATION_URL)
    public User register(@Valid @RequestBody UserRequest newUser) {
        return this.userService.register(newUser);
    }
}
