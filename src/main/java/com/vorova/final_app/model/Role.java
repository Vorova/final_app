package com.vorova.final_app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    private Long id;

    @Column
    private String authority;

    @ManyToOne
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private User user;

    public Role(long id, String authority) {
        this.id = id;
        this.authority = authority;
    }
}
