package project.ecommerce.api.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.ecommerce.api.user.dto.request.*;
import project.ecommerce.api.user.dto.response.UserResponse;
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
        return ResponseEntity.ok(userService.signup(request));
    }

    @Operation(summary = "회원 프로필 조회 API")
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@Parameter(hidden = true) @Auth User user) {
        return ResponseEntity.ok(UserResponse.of(user));
    }

    @Operation(summary = "비밀번호 검증 API")
    @PostMapping("/password")
    public ResponseEntity<?> validatePassword(
            @Parameter(hidden = true) @Auth User user,
            @Valid @RequestBody UserPasswordRequest request) {
        return ResponseEntity.ok(userService.validatePassword(request, user.getId()));
    }

    @Operation(summary = "비밀번호 변경 API")
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(
            @Parameter(hidden = true) @Auth User user,
            @Valid @RequestBody UserPasswordRequest request) {
        return ResponseEntity.ok(userService.updatePassword(request, user.getId()));
    }

    @Operation(summary = "회원 프로필 이미지 수정 API")
    @PutMapping("/profile-image")
    public ResponseEntity<?> updateProfileImage(
            @Parameter(hidden = true) @Auth User user,
            @Valid @RequestBody UserProfileImageRequest request) {
        return ResponseEntity.ok(userService.updateProfileImage(request, user.getId()));
    }

    @Operation(summary = "회원 프로필 수정 API")
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @Parameter(hidden = true) @Auth User user,
            @Valid @RequestBody UserProfileRequest request) {
        return ResponseEntity.ok(userService.updateProfile(request, user.getId()));
    }

    @Operation(summary = "회원 배송지 등록 API")
    @PostMapping("/address")
    public ResponseEntity<?> addAddress(
            @Parameter(hidden = true) @Auth User user,
            @Valid @RequestBody AddressRequest request) {
        return ResponseEntity.ok(userService.addAddress(request, user));
    }

    @Operation(summary = "회원 배송지 수정 API")
    @PutMapping("/address/{addressId}")
    public ResponseEntity<?> updateAddress(
            @Parameter(hidden = true) @Auth User user,
            @PathVariable Long addressId,
            @Valid @RequestBody AddressRequest request) {
        return ResponseEntity.ok(userService.updateAddress(request, addressId, user));
    }

    @Operation(summary = "기본 배송지 변경 API",
            description = "기본 배송지로 설정할 경우 다른 배송지는 설정 해제됩니다.")
    @PutMapping("/address/default/{addressId}")
    public ResponseEntity<?> updateDefaultAddress(
            @Parameter(hidden = true) @Auth User user,
            @PathVariable Long addressId) {
        return ResponseEntity.ok(userService.updateDefaultAddress(addressId, user));
    }

    @Operation(summary = "회원 배송지 삭제 API")
    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<?> deleteAddress(
            @Parameter(hidden = true) @Auth User user,
            @PathVariable Long addressId) {
        userService.deleteAddress(addressId, user);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회원탈퇴 API")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteUser(@Auth User user) {
        userService.deleteUser(user.getId());
        return ResponseEntity.ok().build();
    }
}
