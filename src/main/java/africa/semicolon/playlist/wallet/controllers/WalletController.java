package africa.semicolon.playlist.wallet.controllers;

import africa.semicolon.playlist.wallet.dtos.requests.WithdrawRequest;
import africa.semicolon.playlist.wallet.dtos.responses.WithdrawResponse;
import africa.semicolon.playlist.wallet.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/wallet")
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/withdraw")
    public ResponseEntity<WithdrawResponse> withdraw(@RequestBody WithdrawRequest withdrawRequest) throws IOException {

        WithdrawResponse withdrawResponse = walletService.withdraw(withdrawRequest);

        return new ResponseEntity<>(withdrawResponse, HttpStatus.OK);
    }
}
