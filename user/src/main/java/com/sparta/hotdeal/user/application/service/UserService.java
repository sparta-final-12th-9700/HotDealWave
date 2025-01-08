package com.sparta.hotdeal.user.application.service;

import com.sparta.hotdeal.user.application.dtos.users.request.ReqPatchUsersInfoByIdDto;
import com.sparta.hotdeal.user.application.dtos.users.request.ReqPatchUsersPasswordByIdDto;
import com.sparta.hotdeal.user.application.dtos.users.response.ResDeleteUsersByIdDto;
import com.sparta.hotdeal.user.application.dtos.users.response.ResGetUsersByIdDto;
import com.sparta.hotdeal.user.application.dtos.users.response.ResPatchUsersInfoByIdDto;
import com.sparta.hotdeal.user.application.dtos.users.response.ResPatchUsersPasswordByIdDto;
import com.sparta.hotdeal.user.application.exception.ErrorMessage;
import com.sparta.hotdeal.user.domain.entity.User;
import com.sparta.hotdeal.user.domain.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResGetUsersByIdDto getUser(UUID userId, UUID requestUserId, String requestUserRole) {
        if (!requestUserRole.equals("ROLE_MASTER")) {
            checkOwnUser(userId, requestUserId);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.USER_NOT_FOUND.getMessage()));

        return ResGetUsersByIdDto.from(user);
    }

    @Transactional
    public ResPatchUsersInfoByIdDto updateUser(
            UUID userId,
            ReqPatchUsersInfoByIdDto requestDto,
            UUID requestUserId,
            String requestUserRole
    ) {

        if (!requestUserRole.equals("ROLE_MASTER")) {
            checkOwnUser(userId, requestUserId);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.USER_NOT_FOUND.getMessage()));

        user.updateUser(requestDto.getNickname());

        return ResPatchUsersInfoByIdDto.builder()
                .userId(user.getUserId())
                .build();
    }

    @Transactional
    public ResPatchUsersPasswordByIdDto updateUserPassword(
            UUID userId,
            ReqPatchUsersPasswordByIdDto requestDto,
            UUID requestUserId
    ) {

        checkOwnUser(userId, requestUserId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.USER_NOT_FOUND.getMessage()));

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        user.updatePassword(encodedPassword);

        return ResPatchUsersPasswordByIdDto.builder()
                .userId(user.getUserId())
                .build();
    }

    @Transactional
    public ResDeleteUsersByIdDto deleteUser(
            UUID userId,
            UUID requestUserId,
            String requestUserRole
    ) {

        if (!requestUserRole.equals("ROLE_MASTER")) {
            checkOwnUser(userId, requestUserId);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.USER_NOT_FOUND.getMessage()));

        user.updateDeleted(userId.toString());

        return ResDeleteUsersByIdDto.builder()
                .userId(user.getUserId())
                .build();
    }

    private void checkOwnUser(UUID userId, UUID requestUserId) {
        if (!userId.equals(requestUserId)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_ALLOWED_CONTENT.getMessage());
        }
    }
}
