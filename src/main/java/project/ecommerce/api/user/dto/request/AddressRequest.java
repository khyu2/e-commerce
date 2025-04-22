package project.ecommerce.api.user.dto.request;

import jakarta.validation.constraints.NotNull;

public record AddressRequest(
        @NotNull(message = "수령인은 공백일 수 없습니다.") String receiver,
        @NotNull(message = "주소는 공백일 수 없습니다.") String address,
        @NotNull(message = "우편번호는 공백일 수 없습니다.") Long postalCode,
        @NotNull(message = "연락처는 공백일 수 없습니다.") String phone
) {
}
