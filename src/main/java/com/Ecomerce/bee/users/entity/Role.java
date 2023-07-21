package com.Ecomerce.bee.users.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    private String name;
    private String roleDescription;

    public Role(String name) {
        this.name = name;
    }

}