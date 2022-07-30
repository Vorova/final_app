package com.vorova.final_app.controller;

import com.vorova.final_app.dto.UserDTO;
import com.vorova.final_app.model.User;
import com.vorova.final_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = new ArrayList<>();
        for (User user : userService.allUsers()) {
            users.add(userService.getUserDTO(user));
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/getUser/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        User user = userService.getById(id);
        return new ResponseEntity<>(userService.getUserDTO(user), HttpStatus.OK);
    }

    @PutMapping("/updateUser")
    public void updateUser(@RequestBody User user) {
        userService.update(user);
    }

    @DeleteMapping(value="/deleteUser/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.delete(id);
    }

    @PostMapping("/addUser")
    public void add(@RequestBody User user) {
        userService.save(user);
    }
}
