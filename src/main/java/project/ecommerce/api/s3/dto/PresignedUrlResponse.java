package project.ecommerce.api.s3.dto;

public record PresignedUrlResponse(
        String presignedUrl,
        String uploadUrl
) {
    public static PresignedUrlResponse of(String presignedUrl) {
        return new PresignedUrlResponse(presignedUrl, null);
    }

    public static PresignedUrlResponse of(String presignedUrl, String uploadUrl) {
        return new PresignedUrlResponse(presignedUrl, uploadUrl);
    }
}
