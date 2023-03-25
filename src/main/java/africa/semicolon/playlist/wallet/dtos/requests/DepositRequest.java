package africa.semicolon.playlist.wallet.dtos.requests;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositRequest {

    private String emailAddress;
    private BigDecimal amount;
}
