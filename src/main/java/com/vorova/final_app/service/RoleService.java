package com.vorova.final_app.service;

import com.vorova.final_app.model.Role;

public interface RoleService {

    void addRole(Role role);

    Role getRoleById(Long id);

}
