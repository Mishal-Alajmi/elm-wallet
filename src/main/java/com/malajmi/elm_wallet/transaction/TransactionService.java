package com.malajmi.elm_wallet.transaction;

import com.malajmi.elm_wallet.transaction.enums.TransactionStatus;
import com.malajmi.elm_wallet.transaction.enums.TransactionType;
import com.malajmi.elm_wallet.transaction.models.TransactionsQueryParams;
import com.malajmi.elm_wallet.transaction.models.TransactionsResponse;
import com.malajmi.elm_wallet.user.WalletUserEntity;
import com.malajmi.elm_wallet.wallet.WalletEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public static TransactionEntity createTransaction(WalletEntity wallet, BigDecimal amount, TransactionType type) {
        return TransactionEntity.builder()
                .transactionId(UUID.randomUUID())
                .amount(amount)
                .wallet(wallet)
                .type(type)
                .createdAt(Instant.now())
                .status(TransactionStatus.PENDING)
                .build();
    }

    public void updateTransaction(TransactionEntity transaction) {
        transactionRepository
                .updateStatusByTransactionId(transaction.getStatus(), transaction.getTransactionId());
    }

    public Page<TransactionsResponse> getTransactions(TransactionsQueryParams params, Pageable pageable) {
        var transactionSpec = buildSpecification(params);
        var pagedTransactions = transactionRepository.findAll(transactionSpec, pageable);
        return toTransactionResponse(pagedTransactions);
    }

    private static Page<TransactionsResponse> toTransactionResponse(Page<TransactionEntity> pagedTransactions) {
        var transactions = pagedTransactions
                .getContent()
                .stream()
                .map(TransactionService::toSingleTransactionResponse)
                .toList();
        return new PageImpl<>(transactions, pagedTransactions.getPageable(), pagedTransactions.getTotalElements());
    }

    private static TransactionsResponse toSingleTransactionResponse(TransactionEntity transaction) {
        return TransactionsResponse.builder()
                .transactionId(transaction.getTransactionId())
                .type(transaction.getType())
                .date(transaction.getCreatedAt())
                .amount(transaction.getAmount())
                .status(transaction.getStatus())
                .build();
    }

    private Specification<TransactionEntity> buildSpecification(TransactionsQueryParams params) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (params.getType() != null) {
                predicates.add(cb.equal(root.get("type"), params.getType()));
            }
            if (params.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), params.getStatus()));
            }
            if (params.getStartDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), params.getStartDate()));
            }
            if (params.getEndDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), params.getEndDate()));
            }

            if (params.getUserId() != null) {
                Join<TransactionEntity, WalletEntity> walletJoin = root.join("wallet");
                Join<WalletEntity, WalletUserEntity> userJoin = walletJoin.join("user");
                predicates.add(cb.equal(userJoin.get("id"), params.getUserId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
