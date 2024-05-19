package com.example.workoutWonderland.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequestDTO {
    @NotBlank(message = "User name should not be empty")
    @Size(min = 3, message = "User name should be more than 2 letters long")
    private String name;
    @NotBlank(message = "User lastname should not be empty")
    @Size(min = 3, message = "User lastname should be more than 2 letters long")
    private String lastname;
    @Email
    @NotBlank(message = "username should not be empty")
    private String username;
    @NotBlank(message = "password should not be empty")
    private String password;
}
