package project.ecommerce.api.s3.dto;

public record PresignedUrlRequest(
        String uploadUrl
) {}