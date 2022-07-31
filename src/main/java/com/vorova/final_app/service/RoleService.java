package com.vorova.final_app.service;

import com.vorova.final_app.dto.RoleDTO;
import com.vorova.final_app.model.Role;

import java.util.Set;

public interface RoleService {
    void addRole(Role role);
    Role getRoleById(Long id);
    Set<RoleDTO> getAllRoles();
}
