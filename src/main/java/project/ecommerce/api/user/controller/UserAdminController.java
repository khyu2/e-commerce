package project.ecommerce.api.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.ecommerce.api.user.service.UserService;

@Tag(name = "User Admin API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/users")
public class UserAdminController {

    private final UserService userService;

    @Operation(summary = "회원 로그인 활동 조회")
    @GetMapping("/act/{userId}")
    public ResponseEntity<?> activateUser(@PathVariable Long userId) {
        return ResponseEntity.ok().build();
    }
}
