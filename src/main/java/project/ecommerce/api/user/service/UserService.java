package project.ecommerce.api.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.ecommerce.api.s3.service.S3Service;
import project.ecommerce.api.user.dto.request.*;
import project.ecommerce.api.user.dto.response.AddressResponse;
import project.ecommerce.api.user.dto.response.UserResponse;
import project.ecommerce.api.user.entity.Address;
import project.ecommerce.api.user.entity.User;
import project.ecommerce.api.user.exception.UserExceptionType;
import project.ecommerce.api.user.repository.AddressRepository;
import project.ecommerce.api.user.repository.UserRepository;
import project.ecommerce.common.exception.BaseException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final S3Service s3Service;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    private static final int ADDRESS_LIMIT = 5;

    // TODO: password validation
    @Transactional
    public UserResponse signup(UserSignupRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BaseException(UserExceptionType.USER_ALREADY_EXISTS);
        }

        User entity = request.toEntity();
        entity.encodePassword(passwordEncoder.encode(entity.getPassword()));

        User user = userRepository.save(entity);

        log.info("회원가입 성공: {}", user.getEmail());

        return UserResponse.of(user);
    }

    @Transactional(readOnly = true)
    public boolean validatePassword(UserPasswordRequest request, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));

        return passwordEncoder.matches(request.password(), user.getPassword());
    }

    @Transactional
    public UserResponse updatePassword(UserPasswordRequest request, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));

        user.encodePassword(passwordEncoder.encode(request.password()));
        return UserResponse.of(userRepository.save(user));
    }

    @Transactional
    public UserResponse updateProfileImage(UserProfileImageRequest request, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));

        if (request.profileImageUrl() == null) {
            user.updateProfileImageUrl(null);
        } else if (!s3Service.isFileUploaded(request.profileImageUrl())) {
            throw new BaseException(UserExceptionType.INVALID_PROFILE_IMAGE_URL);
        } else {
            user.updateProfileImageUrl(request.profileImageUrl());
        }

        return UserResponse.of(userRepository.save(user));
    }

    @Transactional
    public UserResponse updateProfile(UserProfileRequest request, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));

        // TODO: validate name

        user.updateProfile(request.name());
        return UserResponse.of(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public List<AddressResponse> getAddresses(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));

        List<Address> addresses = addressRepository.findAllByUser(user);

        Address defaultAddress = addresses.stream()
                .filter(Address::getIsDefault)
                .findFirst()
                .orElseThrow(() -> new BaseException(UserExceptionType.DEFAULT_ADDRESS_NOT_FOUND));

        // defaultAddress를 맨 앞에 추가
        List<AddressResponse> response = new ArrayList<>();
        response.add(AddressResponse.of(defaultAddress));

        addresses.stream()
                .filter(address -> !address.getIsDefault())
                .forEach(address -> response.add(AddressResponse.of(address)));

        return response;
    }

    @Transactional
    public List<AddressResponse> addAddress(AddressRequest request, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));
        List<Address> addresses = addressRepository.findAllByUser(user);

        if (addresses.size() >= ADDRESS_LIMIT) {
            throw new BaseException(UserExceptionType.ADDRESS_LIMIT_EXCEEDED);
        }

        Address address = Address.of(request);
        address.addUser(user);

        if (user.getAddresses().size() == 1) {
            address.updateIsDefault(true);
        }

        addressRepository.save(address);
        return addressRepository.findAllByUser(user)
                .stream().map(AddressResponse::of)
                .toList();
    }

    @Transactional
    public List<AddressResponse> updateAddress(AddressRequest request, Long addressId, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));
        if (user.getAddresses().size() >= ADDRESS_LIMIT
                || user.getAddresses().isEmpty()) {
            throw new BaseException(UserExceptionType.ADDRESS_LIMIT_EXCEEDED);
        }

        Address address = user.getAddresses().stream()
                .filter(a -> a.getId().equals(addressId))
                .findFirst()
                .orElseThrow(() -> new BaseException(UserExceptionType.INVALID_ADDRESS));

        address.updateAddress(request.receiver(), request.address(), request.postalCode(), request.phone());

        addressRepository.save(address);

        return addressRepository.findAllByUser(user)
                .stream().map(AddressResponse::of)
                .toList();
    }

    @Transactional
    public List<AddressResponse> updateDefaultAddress(Long addressId, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));
        Address address = user.getAddresses().stream()
                .filter(a -> a.getId().equals(addressId))
                .findFirst()
                .orElseThrow(() -> new BaseException(UserExceptionType.INVALID_ADDRESS));

        user.getAddresses().forEach(a -> a.updateIsDefault(false));
        address.updateIsDefault(true);

        addressRepository.save(address);

        return addressRepository.findAllByUser(user)
                .stream().map(AddressResponse::of)
                .toList();
    }

    @Transactional
    public List<AddressResponse> deleteAddress(Long addressId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));
        List<Address> addresses = user.getAddresses();

        if (addresses.size() == 1) {
            throw new BaseException(UserExceptionType.ADDRESS_UNDERFLOW);
        }

        Address addressToDelete = addresses.stream()
                .filter(a -> a.getId().equals(addressId))
                .findFirst()
                .orElseThrow(() -> new BaseException(UserExceptionType.INVALID_ADDRESS));

        boolean wasDefault = addressToDelete.getIsDefault();

        addresses.remove(addressToDelete);
        addressRepository.delete(addressToDelete);

        if (wasDefault) {
            addresses.forEach(a -> a.updateIsDefault(false));
            addresses.get(0).updateIsDefault(true);
        }

        return addressRepository.findAllByUser(user)
                .stream().map(AddressResponse::of)
                .toList();
    }


    @Transactional
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));

        userRepository.delete(user);
    }

    public UserResponse getUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));

        return UserResponse.of(user);
    }
}
