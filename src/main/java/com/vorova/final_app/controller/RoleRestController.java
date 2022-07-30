package com.vorova.final_app.controller;

import com.vorova.final_app.dto.RoleDTO;
import com.vorova.final_app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class RoleRestController {

    private final RoleService roleService;

    @Autowired
    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(value = "/api/admin/getAllRoles")
    public Set<RoleDTO> getAllRoles() {
        return roleService.getAllRoles();
    }
}
