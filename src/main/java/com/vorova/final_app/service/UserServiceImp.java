package com.vorova.final_app.service;

import com.vorova.final_app.dto.RoleDTO;
import com.vorova.final_app.dto.UserDTO;
import com.vorova.final_app.model.Role;
import com.vorova.final_app.model.User;
import com.vorova.final_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void add(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getByUsername(String username) {
        Optional<User> user = userRepository.findUserByEmail(username);
        return user.orElse(null);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        User user = getById(id);
        userRepository.delete(user);
    }

    @Override
    public void update(User user) {
        Optional<User> userDB = userRepository.findById(user.getId());
        if(user.getPassword().equals("")) {
            user.setPassword(userDB.get().getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    public UserDTO getUserDTO(User user) {

        Set<RoleDTO> rolesDTO = new HashSet<>();
        for(Role role : user.getRoles()) {
            rolesDTO.add(new RoleDTO(
                    role.getId(),
                    role.getAuthority()
            ));
        }

        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getAge(),
                rolesDTO);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(username);
        if(user.isPresent()) {
            return userRepository.findUserByEmail(username).get();
        } else {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
    }
}