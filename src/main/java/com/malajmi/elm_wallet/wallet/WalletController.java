package com.malajmi.elm_wallet.wallet;

import com.malajmi.elm_wallet.transaction.TransactionEntity;
import com.malajmi.elm_wallet.transaction.models.TransactionsQueryParams;
import com.malajmi.elm_wallet.transaction.models.TransactionsResponse;
import com.malajmi.elm_wallet.wallet.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/add-funds")
    public AddFundsResponse addFunds(@Valid @RequestBody AddFundsRequest request) {
        return walletService.addFunds(request);
    }

    @PostMapping("/transfer")
    public TransferResponse transfer(@Valid @RequestBody TransferRequest request) {
        return walletService.transfer(request);
    }

    @PostMapping("/withdraw")
    public BankTransferResponse withdraw(@Valid @RequestBody BankTransferRequest request) {
        return walletService.withdraw(request);
    }

    @GetMapping("/transactions")
    public Page<TransactionsResponse> getTransactions(TransactionsQueryParams params) {
        Pageable pageable = PageRequest.of(
                params.getPage(),
                params.getSize(),
                Sort.by(Sort.Direction.fromString(params.getSortDirection()), params.getSortBy())
        );
        return walletService.getTransactions(params, pageable);
    }
}
