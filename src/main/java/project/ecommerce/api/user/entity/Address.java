package project.ecommerce.api.user.entity;

import jakarta.persistence.*;
import lombok.*;
import project.ecommerce.api.user.dto.request.AddressRequest;
import project.ecommerce.common.utils.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tb_address")
public class Address extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String receiver;

    private String address;

    private Long postalCode;

    private String phone;

    private Boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static Address of(AddressRequest request) {
        return Address.builder()
                .receiver(request.receiver())
                .address(request.address())
                .postalCode(request.postalCode())
                .phone(request.phone())
                .isDefault(false)
                .build();
    }

    public void addUser(User user) {
        this.user = user;
        user.getAddresses().add(this);
    }

    public void updateAddress(String receiver, String address, Long postalCode, String phone) {
        this.receiver = receiver;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public void updateIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
}
