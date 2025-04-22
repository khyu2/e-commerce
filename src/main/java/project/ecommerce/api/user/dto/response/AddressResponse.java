package project.ecommerce.api.user.dto.response;

import project.ecommerce.api.user.entity.Address;

public record AddressResponse(
        Long id,
        String receiver,
        String address,
        Long postalCode,
        String phone,
        Boolean isDefault
) {
    public static AddressResponse of(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getReceiver(),
                address.getAddress(),
                address.getPostalCode(),
                address.getPhone(),
                address.getIsDefault()
        );
    }
}
