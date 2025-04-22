package project.ecommerce.api.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.ecommerce.api.s3.service.S3Service;
import project.ecommerce.api.user.dto.request.UserPasswordRequest;
import project.ecommerce.api.user.dto.request.UserProfileImageRequest;
import project.ecommerce.api.user.dto.request.UserProfileRequest;
import project.ecommerce.api.user.dto.request.UserSignupRequest;
import project.ecommerce.api.user.dto.response.UserResponse;
import project.ecommerce.api.user.entity.User;
import project.ecommerce.api.user.exception.UserExceptionType;
import project.ecommerce.api.user.repository.UserRepository;
import project.ecommerce.common.exception.BaseException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final S3Service s3Service;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

    public boolean validatePassword(UserPasswordRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));

        return passwordEncoder.matches(request.password(), user.getPassword());
    }

    @Transactional
    public UserResponse updatePassword(UserPasswordRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));

        user.encodePassword(passwordEncoder.encode(request.password()));
        return UserResponse.of(userRepository.save(user));
    }

    @Transactional
    public UserResponse updateProfileImage(UserProfileImageRequest request, Long userId) {
        User user = userRepository.findById(userId)
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
    public UserResponse updateProfile(UserProfileRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));

        // TODO: validate name

        user.updateProfile(request.name());
        return UserResponse.of(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));

        userRepository.delete(user);
    }

    public UserResponse getUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));

        return UserResponse.of(user);
    }
}
