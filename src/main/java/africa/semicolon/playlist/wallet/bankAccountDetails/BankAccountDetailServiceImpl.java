package africa.semicolon.playlist.wallet.bankAccountDetails;

import africa.semicolon.playlist.config.paystack.PaystackConfiguration;
import africa.semicolon.playlist.exception.bankAccountDetailsExceptions.BankAccountDetailsAlreadyExistsException;
import africa.semicolon.playlist.user.models.User;
import africa.semicolon.playlist.user.repositories.UserRepository;
import africa.semicolon.playlist.wallet.bankAccountDetails.dtos.requests.AddBankAccountDetailsRequest;
import africa.semicolon.playlist.wallet.bankAccountDetails.dtos.requests.DeleteBankAccountDetailsRequest;
import africa.semicolon.playlist.wallet.bankAccountDetails.dtos.requests.ResolveAccountRequest;
import africa.semicolon.playlist.wallet.bankAccountDetails.dtos.responses.BankAccountDetailsResponse;
import africa.semicolon.playlist.wallet.bankAccountDetails.dtos.responses.ResolveAccountResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankAccountDetailServiceImpl implements BankAccountDetailService{

    private final PaystackConfiguration paystackConfiguration;
    private final ObjectMapper objectMapper;
    private final BankAccountDetailRepository bankAccountDetailRepository;
    private final UserRepository userRepository;

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

        String responseInJsonFormat = response.body().string();
        String accountName = getAccountNameFrom(responseInJsonFormat);
        return ResolveAccountResponse.builder().data(accountName).build();
    }

    private String getAccountNameFrom(String responseInJsonFormat) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(responseInJsonFormat);
        return jsonNode.path("data").path("account_name").asText();
    }

    @Override
    public BankAccountDetailsResponse addBankAccountDetails(
            AddBankAccountDetailsRequest addBankAccountDetailsRequest) {

        Optional<BankAccountDetail> foundBankAccountDetails =
                bankAccountDetailRepository.findBankAccountDetailByAccountNumber(
                addBankAccountDetailsRequest.getAccountNumber()
        );

        if(foundBankAccountDetails.isPresent()){
            throw new BankAccountDetailsAlreadyExistsException();
        }

        BankAccountDetail savedBankAccountDetails = createAndSaveBankAccountDetails(addBankAccountDetailsRequest);

        addNewBankAccountDetailsToUserList(savedBankAccountDetails, addBankAccountDetailsRequest.getEmailAddress());

        return BankAccountDetailsResponse.builder().isSuccessful(true).build();
    }

    private BankAccountDetail createAndSaveBankAccountDetails(AddBankAccountDetailsRequest addBankAccountDetailsRequest) {
        BankAccountDetail bankAccountDetail = BankAccountDetail.builder()
                .bankCode(addBankAccountDetailsRequest.getBankCode())
                .bankName(addBankAccountDetailsRequest.getBankName())
                .accountName(addBankAccountDetailsRequest.getAccountName())
                .accountNumber(addBankAccountDetailsRequest.getAccountNumber())
                .build();

        return bankAccountDetailRepository.save(bankAccountDetail);
    }

    private void addNewBankAccountDetailsToUserList(BankAccountDetail savedBankAccountDetail, String emailAddress) {
        Optional<User> foundUser = userRepository.findUserByEmailAddress(emailAddress);
        foundUser.ifPresent(user -> user.addBankAccountDetails(savedBankAccountDetail));
        foundUser.ifPresent(userRepository::save);
    }

    @Override
    public BankAccountDetailsResponse deleteBankAccountDetail(
            DeleteBankAccountDetailsRequest deleteBankAccountDetailsRequest) {
        Optional<BankAccountDetail> bankAccountDetail =
                bankAccountDetailRepository.findBankAccountDetailByAccountNumber(
                        deleteBankAccountDetailsRequest.getAccountNumber()
                );

        if(bankAccountDetail.isPresent()){
            removeBankAccountDetailsFromUserList(deleteBankAccountDetailsRequest.getEmailAddress(), bankAccountDetail.get());
            bankAccountDetailRepository.deleteBankAccountDetailByAccountNumber(
                    deleteBankAccountDetailsRequest.getAccountNumber());
        }

        return BankAccountDetailsResponse.builder().isSuccessful(true).build();
    }

    private void removeBankAccountDetailsFromUserList(String emailAddress, BankAccountDetail bankAccountDetail) {
        Optional<User> foundUser = userRepository.findUserByEmailAddress(emailAddress);
        foundUser.ifPresent(user -> user.removeBankAccountDetails(bankAccountDetail));
        userRepository.save(foundUser.get());
    }
}
