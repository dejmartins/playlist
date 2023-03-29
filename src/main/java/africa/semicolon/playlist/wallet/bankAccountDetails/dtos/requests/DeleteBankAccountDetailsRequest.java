package africa.semicolon.playlist.wallet.bankAccountDetails.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class DeleteBankAccountDetailsRequest {

    private String emailAddress;
    private String accountNumber;
}
