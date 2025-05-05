package project.ecommerce.api.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.ecommerce.api.user.dto.request.*;
import project.ecommerce.api.user.dto.response.AddressResponse;
import project.ecommerce.api.user.service.UserService;
import project.ecommerce.common.auth.resolver.Auth;

import java.util.List;

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
    public ResponseEntity<?> getProfile(@Parameter(hidden = true) @Auth String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }

    @Operation(summary = "비밀번호 검증 API")
    @PostMapping("/password")
    public ResponseEntity<?> validatePassword(
            @Parameter(hidden = true) @Auth String username,
            @Valid @RequestBody UserPasswordRequest request) {
        return ResponseEntity.ok(userService.validatePassword(request, username));
    }

    @Operation(summary = "비밀번호 변경 API")
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(
            @Parameter(hidden = true) @Auth String username,
            @Valid @RequestBody UserPasswordRequest request) {
        return ResponseEntity.ok(userService.updatePassword(request, username));
    }

    @Operation(summary = "회원 프로필 이미지 수정 API")
    @PutMapping("/profile-image")
    public ResponseEntity<?> updateProfileImage(
            @Parameter(hidden = true) @Auth String username,
            @Valid @RequestBody UserProfileImageRequest request) {
        return ResponseEntity.ok(userService.updateProfileImage(request, username));
    }

    @Operation(summary = "회원 프로필 수정 API")
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @Parameter(hidden = true) @Auth String username,
            @Valid @RequestBody UserProfileRequest request) {
        return ResponseEntity.ok(userService.updateProfile(request, username));
    }

    @Operation(summary = "회원 배송지 목록 조회 API")
    @GetMapping("/address")
    public ResponseEntity<List<AddressResponse>> getAddressList(@Parameter(hidden = true) @Auth String username) {
        return ResponseEntity.ok(userService.getAddresses(username));
    }

    @Operation(summary = "회원 배송지 등록 API")
    @PostMapping("/address")
    public ResponseEntity<?> addAddress(
            @Parameter(hidden = true) @Auth String username,
            @Valid @RequestBody AddressRequest request) {
        return ResponseEntity.ok(userService.addAddress(request, username));
    }

    @Operation(summary = "회원 배송지 수정 API")
    @PutMapping("/address/{addressId}")
    public ResponseEntity<?> updateAddress(
            @Parameter(hidden = true) @Auth String username,
            @PathVariable Long addressId,
            @Valid @RequestBody AddressRequest request) {
        return ResponseEntity.ok(userService.updateAddress(request, addressId, username));
    }

    @Operation(summary = "기본 배송지 변경 API",
            description = "기본 배송지로 설정할 경우 다른 배송지는 설정 해제됩니다.")
    @PutMapping("/address/default/{addressId}")
    public ResponseEntity<?> updateDefaultAddress(
            @Parameter(hidden = true) @Auth String username,
            @PathVariable Long addressId) {
        return ResponseEntity.ok(userService.updateDefaultAddress(addressId, username));
    }

    @Operation(summary = "회원 배송지 삭제 API")
    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<?> deleteAddress(
            @Parameter(hidden = true) @Auth String username,
            @PathVariable Long addressId) {
        return ResponseEntity.ok(userService.deleteAddress(addressId, username));
    }

    @Operation(summary = "회원탈퇴 API")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteUser(@Auth String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok().build();
    }
}
