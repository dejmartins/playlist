package africa.semicolon.playlist.wallet.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
public class InitiateTransferRequest {

    private String source;
    private BigDecimal amount;
    private String reference;
    private String recipient;
    private String reason;
}
