package africa.semicolon.playlist.wallet.services;

import africa.semicolon.playlist.wallet.dtos.requests.DepositRequest;
import africa.semicolon.playlist.wallet.dtos.requests.WithdrawRequest;
import africa.semicolon.playlist.wallet.dtos.responses.WithdrawResponse;

import java.io.IOException;
import java.math.BigDecimal;

public interface WalletService {

    WithdrawResponse withdraw(WithdrawRequest withdrawRequest) throws IOException;
}
