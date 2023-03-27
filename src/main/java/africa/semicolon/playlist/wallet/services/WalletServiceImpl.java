package africa.semicolon.playlist.wallet.services;

import africa.semicolon.playlist.config.paystack.PaystackConfiguration;
import africa.semicolon.playlist.exception.bankAccountDetailsExceptions.BankAccountDetailsNotFound;
import africa.semicolon.playlist.wallet.bankAccountDetails.BankAccountDetail;
import africa.semicolon.playlist.wallet.bankAccountDetails.BankAccountDetailRepository;
import africa.semicolon.playlist.wallet.dtos.requests.InitiateTransferRequest;
import africa.semicolon.playlist.wallet.dtos.requests.WithdrawRequest;
import africa.semicolon.playlist.wallet.dtos.responses.WithdrawResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final BankAccountDetailRepository bankAccountDetailRepository;
    private final PaystackConfiguration paystackConfiguration;
    private final ObjectMapper objectMapper;

    @Override
    public WithdrawResponse withdraw(WithdrawRequest withdrawRequest) throws IOException {
        Optional<BankAccountDetail> bankAccountDetail = bankAccountDetailRepository
                .findBankAccountDetailByAccountNumber(withdrawRequest.getAccountNumber());

        if(bankAccountDetail.isEmpty()) {
            throw new BankAccountDetailsNotFound();
        }

        String recipient = createTransferRecipient(bankAccountDetail.get());
        String transferReference = generateTransferReference();

        InitiateTransferRequest initiateTransferRequest = InitiateTransferRequest.builder()
                .amount(withdrawRequest.getAmount())
                .reason(withdrawRequest.getReason())
                .reference(transferReference)
                .recipient(recipient)
                .build();

        return WithdrawResponse.builder()
                .status(initiateTransfer(initiateTransferRequest))
                .build();
    }

    private String initiateTransfer(InitiateTransferRequest initiateTransferRequest) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("source", initiateTransferRequest.getSource());
        jsonObject.put("amount", initiateTransferRequest.getAmount());
        jsonObject.put("reference", initiateTransferRequest.getReference());
        jsonObject.put("recipient", initiateTransferRequest.getRecipient());
        jsonObject.put("reason", initiateTransferRequest.getReason());

        RequestBody body = RequestBody.create(mediaType, jsonObject.toString());

        Request request = new Request.Builder()
                .url("https://api.paystack.co/transfer")
                .addHeader("Authorization", paystackConfiguration.getSecretKey())
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseInJsonFormat = response.body().string();

        return getStatusFrom(responseInJsonFormat);
    }

    private String getStatusFrom(String responseInJsonFormat) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(responseInJsonFormat);
        return jsonNode.path("status").asText();
    }

    private String generateTransferReference() {
        return UUID.randomUUID().toString();
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
        String responseInJsonFormat = response.body().string();

        return getRecipientFrom(responseInJsonFormat);
    }

    private String getRecipientFrom(String responseInJsonFormat) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(responseInJsonFormat);
        return jsonNode.path("data").path("recipient_code").asText();
    }
}
