package com.example.json_view.controller;

import com.example.json_view.model.User;
import com.example.json_view.model.Views;
import com.example.json_view.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @JsonView(Views.UserSummary.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @JsonView(Views.UserDetails.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUser(id);
    }

    @JsonView(Views.UserSummary.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public String createUser(@Valid @RequestBody User user) {
        userService.addUser(user);
        return "User created";
    }

    @JsonView(Views.UserSummary.class)
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update")
    public String updateUser(@Valid @RequestBody User user) {
        userService.updateUser(user);
        return "User updated";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "User deleted";
    }
}
