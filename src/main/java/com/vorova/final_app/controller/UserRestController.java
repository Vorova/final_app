package com.vorova.final_app.controller;

import com.vorova.final_app.dto.UserDTO;
import com.vorova.final_app.model.User;
import com.vorova.final_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/getUserLogging")
    public ResponseEntity<UserDTO> getUser(Principal principal) {
        User user = userService.getByUsername(principal.getName());
        return new ResponseEntity<>(userService.getUserDTO(user), HttpStatus.OK);
    }
}
