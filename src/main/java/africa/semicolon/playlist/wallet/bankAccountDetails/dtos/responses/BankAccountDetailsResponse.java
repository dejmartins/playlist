package africa.semicolon.playlist.wallet.bankAccountDetails.dtos.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BankAccountDetailsResponse {

    private boolean isSuccessful;
}
