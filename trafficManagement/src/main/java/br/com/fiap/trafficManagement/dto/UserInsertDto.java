package br.com.fiap.trafficManagement.dto;

import br.com.fiap.trafficManagement.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserInsertDto(
        @NotBlank(message = "Email must not be null!")
        @Email(message = "Email has invalid format!")
        String email,

        @NotBlank(message = "Password must not be null!")
        @Size(min = 6, max = 20, message = "Password must have between 6 and 20 characters")
        String password,

        UserRole userRole
) {
    public static UserInsertDto of(
            String email,
            String password,
            UserRole userRole
    ) {
        return new UserInsertDto(email, password, userRole != null ? userRole : UserRole.USER);
    }
}



