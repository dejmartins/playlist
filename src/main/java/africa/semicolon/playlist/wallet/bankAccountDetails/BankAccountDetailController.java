package africa.semicolon.playlist.wallet.bankAccountDetails;

import africa.semicolon.playlist.wallet.bankAccountDetails.dtos.requests.AddBankAccountDetailsRequest;
import africa.semicolon.playlist.wallet.bankAccountDetails.dtos.requests.DeleteBankAccountDetailsRequest;
import africa.semicolon.playlist.wallet.bankAccountDetails.dtos.requests.ResolveAccountRequest;
import africa.semicolon.playlist.wallet.bankAccountDetails.dtos.responses.BankAccountDetailsResponse;
import africa.semicolon.playlist.wallet.bankAccountDetails.dtos.responses.ResolveAccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/addBankAccountDetails")
    public ResponseEntity<BankAccountDetailsResponse> addBankAccountDetails(
            @RequestBody AddBankAccountDetailsRequest addBankAccountDetailsRequest
            ){

        BankAccountDetailsResponse addBankAccountDetailsResponse =
                bankAccountDetailService.addBankAccountDetails(addBankAccountDetailsRequest);

        return new ResponseEntity<>(addBankAccountDetailsResponse, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteAccountDetails")
    public ResponseEntity<BankAccountDetailsResponse> deleteBankAccountDetails(
            @RequestBody DeleteBankAccountDetailsRequest deleteBankAccountDetailsRequest
    ){

        BankAccountDetailsResponse bankAccountDetailsResponse =
                bankAccountDetailService.deleteBankAccountDetail(deleteBankAccountDetailsRequest);

        return new ResponseEntity<>(bankAccountDetailsResponse, HttpStatus.ACCEPTED);
    }

}
