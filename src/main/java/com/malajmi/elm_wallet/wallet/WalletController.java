package com.malajmi.elm_wallet.wallet;

import com.malajmi.elm_wallet.wallet.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
