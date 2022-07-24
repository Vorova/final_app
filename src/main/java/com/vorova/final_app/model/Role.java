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
    private User user;

    public Role(long id, String authority) {
        this.id = id;
        this.authority = authority;
    }
}
