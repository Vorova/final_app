package com.vorova.final_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private byte age;
    private Set<RoleDTO> roles;

}
