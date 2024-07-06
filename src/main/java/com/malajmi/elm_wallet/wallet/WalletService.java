package com.malajmi.elm_wallet.wallet;

import com.malajmi.elm_wallet.exceptions.InvalidParametersException;
import com.malajmi.elm_wallet.exceptions.NotFoundException;
import com.malajmi.elm_wallet.transaction.TransactionEntity;
import com.malajmi.elm_wallet.transaction.TransactionService;
import com.malajmi.elm_wallet.transaction.enums.TransactionStatus;
import com.malajmi.elm_wallet.transaction.enums.TransactionType;
import com.malajmi.elm_wallet.transaction.models.TransactionsQueryParams;
import com.malajmi.elm_wallet.transaction.models.TransactionsResponse;
import com.malajmi.elm_wallet.wallet.models.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final TransactionService transactionService;

    public WalletEntity createWallet() {
        return WalletEntity.builder()
                .walletId(UUID.randomUUID())
                .balance(BigDecimal.ZERO)
                .createdAt(Instant.now())
                .build();

    }

    @Transactional
    public AddFundsResponse addFunds(AddFundsRequest request) {
        if (request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidParametersException("amount must be greater than zero");
        }
        var wallet = walletRepository
                .findByUserId(request.userId())
                .orElseThrow(() -> new NotFoundException("Could not find wallet"));

        var transaction = TransactionService
                .createTransaction(wallet, request.amount(), TransactionType.ADD_FUNDS);

        wallet.setBalance(wallet.getBalance().add(request.amount()));
        wallet.addTransaction(transaction);

        boolean status = true;
        try {
            walletRepository.save(wallet);
            transaction.setStatus(TransactionStatus.SUCCESS);
        } catch (Exception e) {
            status = false;
            transaction.setStatus(TransactionStatus.FAILURE);
        }
        transactionService.updateTransaction(transaction);

        return toAddFundsResponse(wallet, status);
    }

    @Transactional
    public TransferResponse transfer(TransferRequest request) {
        var sender = walletRepository
                .findByUserId(request.senderId())
                .orElseThrow(() -> new NotFoundException("Could not find sender wallet"));
        var recipient = walletRepository
                .findByUserId(request.recipientId())
                .orElseThrow(() -> new NotFoundException("Could not find recipient wallet"));

        if (request.amount().compareTo(sender.getBalance()) > 0) {
            throw new InvalidParametersException("Sender does not have enough balance");
        }

        var transaction = TransactionService
                .createTransaction(sender, request.amount(), TransactionType.TRANSFER);

        sender.setBalance(sender.getBalance().subtract(request.amount()));
        sender.addTransaction(transaction);
        recipient.setBalance(recipient.getBalance().add(request.amount()));

        boolean status = true;
        try {
            walletRepository.saveAll(List.of(sender, recipient));
            transaction.setStatus(TransactionStatus.SUCCESS);
        } catch (Exception e) {
            status = false;
            transaction.setStatus(TransactionStatus.FAILURE);
        }

        transactionService.updateTransaction(transaction);
        return toTransferResponse(request.amount(), status);
    }

    @Transactional
    public BankTransferResponse withdraw(BankTransferRequest request) {
        var wallet = walletRepository
                .findByUserId(request.userId())
                .orElseThrow(() -> new NotFoundException("Could not find wallet"));

        if (request.amount().compareTo(wallet.getBalance()) > 0) {
            throw new InvalidParametersException("Sender does not have enough balance");
        }

        var transaction = TransactionService
                .createTransaction(wallet, request.amount(), TransactionType.WITHDRAW);
        wallet.addTransaction(transaction);
        wallet.setBalance(wallet.getBalance().subtract(request.amount()));

        boolean status = true;
        try {
            walletRepository.save(wallet);
            transaction.setStatus(TransactionStatus.SUCCESS);
        } catch (Exception e) {
            status = false;
            transaction.setStatus(TransactionStatus.FAILURE);
        }
        transactionService.updateTransaction(transaction);
        return toBankTransferResponse(request.amount(), status);
    }

    public Page<TransactionsResponse> getTransactions(TransactionsQueryParams params, Pageable pageable) {
        return transactionService.getTransactions(params, pageable);
    }

    public static BankTransferResponse toBankTransferResponse(BigDecimal amount, boolean status) {
        return BankTransferResponse.builder()
                .newBalance(amount)
                .message("Funds successfully transferred to recipient")
                .success(status)
                .build();
    }

    public static TransferResponse toTransferResponse(BigDecimal amount, boolean status) {
        return TransferResponse.builder()
                .amount(amount)
                .message("Funds successfully transferred to recipient")
                .success(status)
                .build();
    }

    public static AddFundsResponse toAddFundsResponse(WalletEntity wallet, boolean status) {
        return AddFundsResponse.builder()
                .status(status)
                .message("Funds successfully added")
                .newBalance(wallet.getBalance())
                .build();
    }

    public static WalletResponse toWalletResponse(WalletEntity wallet) {
        return WalletResponse.builder()
                .balance(wallet.getBalance())
                .walletId(wallet.getWalletId())
                .createdAt(wallet.getCreatedAt())
                .build();
    }
}
