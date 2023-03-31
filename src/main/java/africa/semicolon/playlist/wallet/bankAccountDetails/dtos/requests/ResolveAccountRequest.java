package africa.semicolon.playlist.wallet.bankAccountDetails.dtos.requests;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResolveAccountRequest {

    private String accountNumber;
    private String bankCode;
}
