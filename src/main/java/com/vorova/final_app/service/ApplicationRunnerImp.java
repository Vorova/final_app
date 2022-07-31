package com.vorova.final_app.service;

import com.vorova.final_app.model.Role;
import com.vorova.final_app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ApplicationRunnerImp implements ApplicationRunner {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationRunnerImp(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        addRoles();
        addAdmin();
        System.err.println(" Приложение успешно запущено! \n");
    }

    private void addAdmin() {
        User admin = new User();
        admin.setEmail("admin@email.ru");
        admin.setFirstName("Vladislav");
        admin.setLastName("Vorobyov");
        admin.setAge((byte) 26);
        admin.setPassword(passwordEncoder.encode("password"));

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleById(1L));
        roles.add(roleService.getRoleById(2L));
        admin.setRoles(roles);

        try {
            userService.add(admin);
            System.err.println(" Администратор успешно создан");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addRoles() {
        Role adminRole = new Role(1L, "ROLE_ADMIN");
        Role userRole = new Role(2L, "ROLE_USER");

        try {
            roleService.addRole(adminRole);
            roleService.addRole(userRole);
            System.err.println(" Роли добавлены в таблицу ролей");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
