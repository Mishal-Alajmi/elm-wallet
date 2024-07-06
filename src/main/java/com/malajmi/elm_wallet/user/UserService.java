package com.malajmi.elm_wallet.user;

import com.malajmi.elm_wallet.exceptions.DuplicateUserException;
import com.malajmi.elm_wallet.user.models.SignupRequest;
import com.malajmi.elm_wallet.user.models.WalletUserResponse;
import com.malajmi.elm_wallet.wallet.WalletService;
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
    private final WalletService walletService;

    public WalletUserResponse createUser(SignupRequest request) {
        var existingUser = walletUserRepository
                .findByUsername(request.username())
                .orElseThrow(() -> new DuplicateUserException(request.username() + " already exists"));

        var wallet = walletService.createWallet();
        var user = WalletUserEntity.builder()
                .externalId(UUID.randomUUID())
                .username(request.username())
                .password(encoder.encode(request.password()))
                .email(request.email())
                .role("USER")
                .createdAt(Instant.now())
                .build();

        wallet.setUser(user);
        user.setWallet(wallet);
        walletUserRepository.save(user);

        return toWalletUserResponse(user);
    }

    private static WalletUserResponse toWalletUserResponse(WalletUserEntity user) {
        var wallet = WalletService.toWalletResponse(user.getWallet());
        return WalletUserResponse.builder()
                .userId(user.getExternalId())
                .username(user.getUsername())
                .email(user.getEmail())
                .wallet(wallet)
                .createdAt(user.getCreatedAt())
                .build();
    }
}
