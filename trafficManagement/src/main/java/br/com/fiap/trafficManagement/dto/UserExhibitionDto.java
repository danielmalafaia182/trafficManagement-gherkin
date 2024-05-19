package br.com.fiap.trafficManagement.dto;

import br.com.fiap.trafficManagement.model.User;
import br.com.fiap.trafficManagement.model.UserRole;

import java.time.LocalDateTime;

public record UserExhibitionDto(
        Long userId,
        String email,
        UserRole userRole,
        LocalDateTime createdAt
) {
    public UserExhibitionDto(User user) {
        this(
                user.getUserId(),
                user.getEmail(),
                user.getUserRole(),
                user.getCreatedAt()
        );
    }
}
