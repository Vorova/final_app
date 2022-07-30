package com.vorova.final_app.service;

import com.vorova.final_app.dto.RoleDTO;
import com.vorova.final_app.model.Role;
import com.vorova.final_app.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImp implements RoleService{

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void addRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findRoleById(id);
    }

    @Override
    public Set<RoleDTO> getAllRoles() {
        Set<RoleDTO> roles = new HashSet<>();
        for(Role role : roleRepository.findAll()) {
            roles.add(new RoleDTO(role.getId(), role.getAuthority()));
        }
        return roles;
    }
}
