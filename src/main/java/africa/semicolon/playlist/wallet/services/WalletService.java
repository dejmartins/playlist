package africa.semicolon.playlist.wallet.services;

import africa.semicolon.playlist.wallet.dtos.requests.DepositRequest;

import java.math.BigDecimal;

public interface WalletService {

    void withdraw(BigDecimal amount);
}
