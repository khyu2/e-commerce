package project.ecommerce.api.user.dto.response;

import project.ecommerce.api.user.entity.User;

public record UserResponse(
        Long id,
        String name,
        String email,
        String profileImageUrl
) {
    public static UserResponse of(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getProfileImageUrl()
        );
    }
}
