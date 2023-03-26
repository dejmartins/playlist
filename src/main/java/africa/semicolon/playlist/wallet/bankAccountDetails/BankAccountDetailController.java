package africa.semicolon.playlist.wallet.bankAccountDetails;

import africa.semicolon.playlist.wallet.bankAccountDetails.dtos.requests.ResolveAccountRequest;
import africa.semicolon.playlist.wallet.bankAccountDetails.dtos.responses.ResolveAccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/bankAccountDetails")
public class BankAccountDetailController {

    private final BankAccountDetailService bankAccountDetailService;

    @GetMapping("/resolveAccount")
    public ResponseEntity<ResolveAccountResponse> resolveAccount(
            @RequestBody ResolveAccountRequest resolveAccountRequest) throws IOException {

        ResolveAccountResponse resolveAccountResponse = ResolveAccountResponse.builder()
                .data(bankAccountDetailService.resolveAccount(resolveAccountRequest))
                .build();

        return new ResponseEntity<>(resolveAccountResponse, HttpStatus.ACCEPTED);
    }

}
