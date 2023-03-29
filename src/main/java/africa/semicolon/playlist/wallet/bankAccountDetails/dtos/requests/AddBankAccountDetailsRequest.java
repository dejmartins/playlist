package africa.semicolon.playlist.wallet.bankAccountDetails.dtos.requests;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddBankAccountDetailsRequest {

    private String bankName;
    private String bankCode;
    private String accountName;
    private String emailAddress;
    private String accountNumber;
}
