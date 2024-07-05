package com.malajmi.elm_wallet.user;

import com.malajmi.elm_wallet.exceptions.DuplicateUserException;
import com.malajmi.elm_wallet.user.models.SignupRequest;
import com.malajmi.elm_wallet.user.models.WalletUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final WalletUserRepository walletUserRepository;
    private final PasswordEncoder encoder;

    public WalletUserResponse createUser(SignupRequest request) {
        var existingUser = walletUserRepository.findByUsername(request.username());
        if (existingUser.isPresent()) {
            throw new DuplicateUserException(request.username() + " already exists");
        }
        var user = walletUserRepository.save(WalletUserEntity.builder()
                .externalId(UUID.randomUUID())
                .username(request.username())
                .password(encoder.encode(request.password()))
                .email(request.email())
                .role("USER")
                .createdAt(Instant.now())
                .build());

        return fromWalletUserEntity(user);
    }

    private static WalletUserResponse fromWalletUserEntity(WalletUserEntity user) {
        return WalletUserResponse.builder()
                .userId(user.getExternalId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
