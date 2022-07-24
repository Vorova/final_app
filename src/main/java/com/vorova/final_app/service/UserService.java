package com.vorova.final_app.service;

import com.vorova.final_app.model.User;

import java.util.List;

public interface UserService {

    void add(User user);

    List<User> allUsers();

    User getByUsername(String username);

    void save(User user, Long[] roles);
}
