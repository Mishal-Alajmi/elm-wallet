package com.malajmi.elm_wallet.transaction;

import com.malajmi.elm_wallet.transaction.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long>,
        JpaSpecificationExecutor<TransactionEntity> {
    @Transactional
    @Modifying
    @Query("update TransactionEntity t set t.status = ?1 where t.transactionId = ?2")
    void updateStatusByTransactionId(TransactionStatus status, UUID transactionId);
}
