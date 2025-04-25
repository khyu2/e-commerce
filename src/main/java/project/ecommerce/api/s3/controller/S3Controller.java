package project.ecommerce.api.s3.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.ecommerce.api.s3.dto.PresignedUrlRequest;
import project.ecommerce.api.s3.service.S3Service;
import project.ecommerce.api.user.entity.User;
import project.ecommerce.common.auth.resolver.Auth;

@Tag(name = "S3 API", description = "Presigned URL 생성용 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/s3")
public class S3Controller {

    private final S3Service s3Service;

    @Operation(summary = "파일 업로드용 presigned-url 생성 API")
    @GetMapping("/upload")
    public ResponseEntity<?> generatePresignedUploadUrl(@Parameter(hidden = true) @Auth User user) {
        return ResponseEntity.ok(s3Service.getUploadUrl(user.getEmail()));
    }

    @Operation(summary = "파일 다운로드용 presigned-url 생성 API")
    @PostMapping("/download")
    public ResponseEntity<?> generatePresignedDownloadUrl(
            @Parameter(hidden = true) @Auth User user,
            @Valid @RequestBody PresignedUrlRequest request) {
        return ResponseEntity.ok(s3Service.getDownloadUrl(request.uploadUrl()));
    }

    @Operation(summary = "파일 삭제 API")
    @DeleteMapping("/{uploadUrl}")
    public ResponseEntity<?> deleteFile(
            @Parameter(hidden = true) @Auth User user,
            @PathVariable String uploadUrl) {
        s3Service.deleteFile(uploadUrl);
        return ResponseEntity.ok().build();
    }
}
