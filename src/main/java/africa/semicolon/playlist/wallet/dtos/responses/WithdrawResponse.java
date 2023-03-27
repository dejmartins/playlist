package africa.semicolon.playlist.wallet.dtos.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WithdrawResponse {

    private String status;
}
