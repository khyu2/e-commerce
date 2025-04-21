package project.ecommerce.api.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.ecommerce.api.user.dto.request.UserPasswordRequest;
import project.ecommerce.api.user.dto.request.UserSignupRequest;
import project.ecommerce.api.user.entity.User;
import project.ecommerce.api.user.service.UserService;
import project.ecommerce.common.auth.resolver.Auth;

@Tag(name = "User API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입 API")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserSignupRequest request) {
        userService.signup(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "비밀번호 검증 API")
    @PostMapping("/password")
    public ResponseEntity<?> validatePassword(
            @Parameter(hidden = true) @Auth User user,
            @Valid @RequestBody UserPasswordRequest request) {
        var isValid = userService.validatePassword(request, user.getId());
        return ResponseEntity.ok(isValid);
    }

    @Operation(summary = "비밀번호 변경 API")
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(
            @Parameter(hidden = true) @Auth User user,
            @Valid @RequestBody UserPasswordRequest request) {
        userService.updatePassword(request, user.getId());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회원탈퇴 API")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteUser(@Auth User user) {
        userService.deleteUser(user.getId());
        return ResponseEntity.ok().build();
    }
}
