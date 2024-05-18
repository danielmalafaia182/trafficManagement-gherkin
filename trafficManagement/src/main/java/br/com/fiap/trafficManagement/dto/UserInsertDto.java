package br.com.fiap.trafficManagement.dto;

import br.com.fiap.trafficManagement.model.User;
import br.com.fiap.trafficManagement.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserInsertDto(
        Long userId,

        @NotBlank(message = "must not be null!")
        @Email(message = "invalid format!")
        String email,

        @NotBlank(message = "must not be null!")
        @Size(min = 6, max = 20, message = "password must have betweeen 6 and 20 characters")
        String password
) {
}


