package africa.semicolon.playlist.wallet.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class WithdrawRequest {

    private String reason;
    private BigDecimal amount;
    private String accountNumber;
}
