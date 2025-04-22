package project.ecommerce.api.user.dto.response;

import project.ecommerce.api.user.entity.User;
import project.ecommerce.api.user.entity.UserRole;
import project.ecommerce.common.utils.TimeUtils;

public record UserResponse(
        Long userId,
        String name,
        String email,
        String profileImageUrl,
        UserRole role,
        String createdAt
) {
    public static UserResponse of(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getProfileImageUrl(),
                user.getRole(),
                TimeUtils.format(user.getCreatedAt())
        );
    }
}
