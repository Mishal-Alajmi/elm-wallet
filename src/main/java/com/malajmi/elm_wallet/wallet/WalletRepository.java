package com.malajmi.elm_wallet.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<WalletEntity, Long> {

    @Query("select w from WalletEntity w where w.user.id = :userId")
    public Optional<WalletEntity> findByUserId(@Param("userId") Long userId);
}
