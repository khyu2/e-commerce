package project.ecommerce.api.s3.dto;

public record PresignedUrlResponse(
        String presignedUrl
) {
    public static PresignedUrlResponse of(String presignedUrl) {
        return new PresignedUrlResponse(presignedUrl);
    }
}
