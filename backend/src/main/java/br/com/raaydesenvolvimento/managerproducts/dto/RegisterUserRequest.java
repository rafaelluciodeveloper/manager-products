package br.com.raaydesenvolvimento.managerproducts.dto;

import br.com.raaydesenvolvimento.managerproducts.model.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {

    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 10, max = 50, message = "Username must be between 10 and 50 characters")
    private String username;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Role role;
}
