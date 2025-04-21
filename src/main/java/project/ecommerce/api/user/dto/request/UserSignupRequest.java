package project.ecommerce.api.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import project.ecommerce.api.user.entity.User;

public record UserSignupRequest(
        @NotNull @Size(min = 4) String name,
        @NotNull @Email String email,
        @NotNull @Size(min = 8, max = 20) String password
) {
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
