package africa.semicolon.playlist.wallet.bankAccountDetails;

import africa.semicolon.playlist.config.paystack.PaystackConfiguration;
import africa.semicolon.playlist.wallet.bankAccountDetails.dtos.requests.ResolveAccountRequest;
import africa.semicolon.playlist.wallet.bankAccountDetails.dtos.responses.ResolveAccountResponse;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BankAccountDetailServiceImpl implements BankAccountDetailService{

    private final PaystackConfiguration paystackConfiguration;

    @Override
    public ResolveAccountResponse resolveAccount(ResolveAccountRequest resolveAccountRequest) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.paystack.co/bank/resolve?account_number="
                        + resolveAccountRequest.getAccountNumber()
                        + "&bank_code="
                        + resolveAccountRequest.getBankCode())
                .get()
                .addHeader("Authorization", "Bearer " + paystackConfiguration.getSecretKey())
                .build();
        Response response = client.newCall(request).execute();
        return null;
    }
}
