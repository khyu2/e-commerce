package project.ecommerce.api.s3.service;

import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.ecommerce.api.s3.dto.PresignedUrlResponse;
import project.ecommerce.common.config.MinioConfig;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final MinioClient minioClient;
    private final MinioConfig minioConfig;

    public PresignedUrlResponse getUploadUrl(String email) {
        String uploadUrl = generateFileUploadUrl(email);

        try {
            ensureBucketExists();
            String presignedUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)  // PUT 메서드 사용
                            .bucket(minioConfig.getBucketName())
                            .object(uploadUrl)
                            .expiry(10, TimeUnit.MINUTES)
                            .build()
            );

            log.info("Presigned Upload URL 생성 성공: {}", presignedUrl);

            return PresignedUrlResponse.of(presignedUrl, uploadUrl);
        } catch (Exception e) {
            throw new RuntimeException("Presigned URL 생성 실패: " + e.getMessage());
        }
    }

    public PresignedUrlResponse getDownloadUrl(String uploadFileUrl) {
        try {
            ensureBucketExists();
            String presignedUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(minioConfig.getBucketName())
                            .object(uploadFileUrl)
                            .expiry(60, TimeUnit.MINUTES)
                            .build()
            );

            log.info("Presigned Download URL 생성 성공: {}", presignedUrl);

            return PresignedUrlResponse.of(presignedUrl);
        } catch (Exception e) {
            throw new RuntimeException("Presigned URL 생성 실패: " + e.getMessage());
        }
    }

    public void deleteFile(String uploadUrl) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(uploadUrl)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("파일 삭제 실패", e);
        }
    }

    public boolean isFileUploaded(String uploadUrl) {
        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(uploadUrl)
                            .build()
            );
            return true;
        } catch (ErrorResponseException e) {
            if (e.errorResponse().code().equals("NoSuchKey")) {
                return false;
            }
            throw new RuntimeException("MinIO 접근 오류", e);
        } catch (Exception e) {
            throw new RuntimeException("MinIO 조회 실패", e);
        }
    }

    private void ensureBucketExists() throws Exception{
        boolean found = minioClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(minioConfig.getBucketName())
                        .build()
        );

        if (!found) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .build()
            );
        }
    }

    private String generateFileUploadUrl(String email) {
        return System.currentTimeMillis() + "_"
                + email + "_"
                + java.util.UUID.randomUUID().toString().substring(0, 5);
    }
}
