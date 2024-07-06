package com.malajmi.elm_wallet.wallet;

import com.malajmi.elm_wallet.exceptions.InvalidParametersException;
import com.malajmi.elm_wallet.exceptions.NotFoundException;
import com.malajmi.elm_wallet.wallet.models.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {
    
    private final WalletRepository walletRepository;

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

        wallet.setBalance(wallet.getBalance().add(request.amount()));

        boolean status = true;
        try {
            walletRepository.save(wallet);
        } catch (Exception e) {
            status = false;
        }

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

        sender.setBalance(sender.getBalance().subtract(request.amount()));
        recipient.setBalance(recipient.getBalance().add(request.amount()));

        boolean status = true;
        try {
            walletRepository.saveAll(List.of(sender, recipient));
        } catch (Exception e) {
            status = false;
        }

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

        wallet.setBalance(wallet.getBalance().subtract(request.amount()));

        boolean status = true;
        try {
            walletRepository.save(wallet);
        } catch (Exception e) {
            status = false;
        }

        return toBankTransferResponse(request.amount(), status);
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
