package africa.semicolon.playlist.wallet;

import africa.semicolon.playlist.wallet.dtos.requests.DepositRequest;

import java.math.BigDecimal;

public interface WalletService {

    void deposit(DepositRequest depositRequest);
    void withdraw(BigDecimal amount);
}
