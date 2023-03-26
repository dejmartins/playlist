package africa.semicolon.playlist.wallet.bankAccountDetails;

import africa.semicolon.playlist.wallet.bankAccountDetails.dtos.requests.ResolveAccountRequest;
import africa.semicolon.playlist.wallet.bankAccountDetails.dtos.responses.ResolveAccountResponse;

import java.io.IOException;

public interface BankAccountDetailService {

    ResolveAccountResponse resolveAccount(ResolveAccountRequest resolveAccountRequest) throws IOException;
}
