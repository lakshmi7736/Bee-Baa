package com.Ecomerce.bee.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotEmpty(message = "Please enter valid name")
    private String name;

    @NotEmpty(message = "Please enter valid email")
    private String email;

    @NotEmpty(message = "Please enter valid password")
    private String password;


}