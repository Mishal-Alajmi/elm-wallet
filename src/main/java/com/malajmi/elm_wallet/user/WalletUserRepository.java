package com.malajmi.elm_wallet.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.Optional;
import java.util.UUID;


public interface WalletUserRepository extends JpaRepository<WalletUserEntity, Long> {

    Optional<WalletUserEntity> findByUsername(String username);
}
