package com.vorova.final_app.service;

import com.vorova.final_app.dto.UserDTO;
import com.vorova.final_app.model.User;

import java.util.List;

public interface UserService {

    void add(User user);

    List<User> allUsers();

    User getByUsername(String username);

    User getById(Long id);

    void save(User user);

    void delete(Long id);

    void update(User user);

    UserDTO getUserDTO(User user);

}
