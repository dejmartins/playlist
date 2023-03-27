package africa.semicolon.playlist.wallet.services;

import africa.semicolon.playlist.config.paystack.PaystackConfiguration;
import africa.semicolon.playlist.exception.bankAccountDetailsExceptions.BankAccountDetailsNotFound;
import africa.semicolon.playlist.wallet.bankAccountDetails.BankAccountDetail;
import africa.semicolon.playlist.wallet.bankAccountDetails.BankAccountDetailRepository;
import africa.semicolon.playlist.wallet.dtos.requests.WithdrawRequest;
import africa.semicolon.playlist.wallet.dtos.responses.WithdrawResponse;
import com.squareup.okhttp.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final BankAccountDetailRepository bankAccountDetailRepository;
    private final PaystackConfiguration paystackConfiguration;

    @Override
    public WithdrawResponse withdraw(WithdrawRequest withdrawRequest) throws IOException {
        Optional<BankAccountDetail> bankAccountDetail = bankAccountDetailRepository
                .findBankAccountDetailByAccountNumber(withdrawRequest.getAccountNumber());

        if(bankAccountDetail.isEmpty()) {
            throw new BankAccountDetailsNotFound();
        }

        String response = createTransferRecipient(bankAccountDetail.get());

        return null;
    }

    private String createTransferRecipient(BankAccountDetail bankAccountDetail) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        JSONObject json = new JSONObject();
        json.put("type", "nuban");
        json.put("name", bankAccountDetail.getAccountName());
        json.put("account_number", bankAccountDetail.getAccountNumber());
        json.put("bank_code", bankAccountDetail.getBankCode());
        json.put("currency", "NGN");

        RequestBody body = RequestBody.create(mediaType, json.toString());

        Request request = new Request.Builder()
                .url("https://api.paystack.co/transferrecipient")
                .addHeader("Authorization", "Bearer " + paystackConfiguration.getSecretKey())
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        return null;
    }
}
