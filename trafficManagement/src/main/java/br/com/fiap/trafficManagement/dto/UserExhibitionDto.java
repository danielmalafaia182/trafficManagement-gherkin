package br.com.fiap.trafficManagement.dto;

import br.com.fiap.trafficManagement.model.User;
import br.com.fiap.trafficManagement.model.UserRole;

public record UserExhibitionDto(
        Long userId,
        String email,
        UserRole userRole
) {
    public UserExhibitionDto(User user) {
        this(
                user.getUserId(),
                user.getEmail(),
                user.getUserRole()
        );
    }
}
