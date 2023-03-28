package africa.semicolon.playlist.wallet.bankAccountDetails;

import africa.semicolon.playlist.exception.bankAccountDetailsExceptions.BankAccountDetailsAlreadyExistsException;
import africa.semicolon.playlist.user.models.User;
import africa.semicolon.playlist.user.repositories.UserRepository;
import africa.semicolon.playlist.wallet.bankAccountDetails.dtos.requests.AddBankAccountDetailsRequest;
import africa.semicolon.playlist.wallet.bankAccountDetails.dtos.requests.ResolveAccountRequest;
import africa.semicolon.playlist.wallet.bankAccountDetails.dtos.responses.ResolveAccountResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class BankAccountDetailServiceImplTest {

    @Autowired
    private BankAccountDetailService bankAccountDetailService;
    @MockBean
    private BankAccountDetailRepository bankAccountDetailRepository;
    @MockBean
    private UserRepository userRepository;

    @Test
    public void resolveAccountTest() throws IOException {
        ResolveAccountRequest resolveAccountRequest = ResolveAccountRequest.builder()
                .accountNumber("2208581273")
                .bankCode("057")
                .build();

        ResolveAccountResponse response = bankAccountDetailService.resolveAccount(resolveAccountRequest);
        String firstName = String.valueOf(response.getData()).split(" ")[0];
        assertEquals("JONATHAN", firstName);
    }

    @Test
    public void couldNotResolveAccountTest() throws IOException {
        ResolveAccountRequest resolveAccountRequest = ResolveAccountRequest.builder()
                .accountNumber("2208588473")
                .bankCode("057")
                .build();

        ResolveAccountResponse response = bankAccountDetailService.resolveAccount(resolveAccountRequest);
        assertEquals("", response.getData());
    }

    @Test
    public void addBankDetailsThatExistsAlready_ThrowsExceptionTest(){
        BankAccountDetail bankAccountDetail = BankAccountDetail.builder()
                .bankName("Zenith Bank")
                .accountName("Jonathan Martins")
                .bankCode("054")
                .accountNumber("1234567890")
                .build();

        when(bankAccountDetailRepository.findBankAccountDetailByAccountNumber("1234567890"))
                .thenReturn(Optional.ofNullable(bankAccountDetail));

        AddBankAccountDetailsRequest request = AddBankAccountDetailsRequest.builder()
                .accountName("Jonathan Martins")
                .accountNumber("1234567890")
                .bankCode("054")
                .bankName("Zenith Bank")
                .build();

        assertThrows(BankAccountDetailsAlreadyExistsException.class,
                () -> bankAccountDetailService.addBankAccountDetails(request));
    }

    @Test
    public void addBankDetailsTest(){
        AddBankAccountDetailsRequest request = AddBankAccountDetailsRequest.builder()
                .accountName("Jonathan Martins")
                .accountNumber("1234567890")
                .bankCode("054")
                .bankName("Zenith Bank")
                .emailAddress("dejimartins@gmail.com")
                .build();

        User user = User.builder()
                .id(1L)
                .firstName("Dej")
                .lastName("Doe")
                .password("12345")
                .emailAddress("dej@gmail.com")
                .wallet(null)
                .build();

        when(userRepository.findUserByEmailAddress("dej@gmail.com")).thenReturn(Optional.ofNullable(user));
        bankAccountDetailService.addBankAccountDetails(request);

        BankAccountDetail bankAccountDetail = BankAccountDetail.builder()
                .bankName("Zenith Bank")
                .accountName("Jonathan Martins")
                .bankCode("054")
                .accountNumber("1234567890")
                .build();

        when(bankAccountDetailRepository.findBankAccountDetailByAccountNumber("1234567890"))
                .thenReturn(Optional.ofNullable(bankAccountDetail));

        BankAccountDetail foundBankAccountDetail =
                bankAccountDetailRepository.findBankAccountDetailByAccountNumber("1234567890").get();

        assertEquals("1234567890", foundBankAccountDetail.getAccountNumber());
    }

    @Test
    public void bankAccountDetailsIsUniqueForEachUserTest(){
        User user = User.builder()
                .id(1L)
                .firstName("Dej")
                .lastName("Doe")
                .password("12345")
                .emailAddress("dej@gmail.com")
                .wallet(null)
                .build();

        when(userRepository.findUserByEmailAddress("dej@gmail.com")).thenReturn(Optional.ofNullable(user));

        BankAccountDetail bankAccountDetail = BankAccountDetail.builder()
                .bankName("Zenith Bank")
                .accountName("Jonathan Martins")
                .bankCode("054")
                .accountNumber("1234567890")
                .build();

        when(bankAccountDetailRepository.findBankAccountDetailByAccountNumber("1234567890"))
                .thenReturn(Optional.ofNullable(bankAccountDetail));

        AddBankAccountDetailsRequest request = AddBankAccountDetailsRequest.builder()
                .accountName("Jonathan Martins")
                .accountNumber("1234567890")
                .bankCode("054")
                .bankName("Zenith Bank")
                .emailAddress("dejimartins@gmail.com")
                .build();


        assertThrows(BankAccountDetailsAlreadyExistsException.class,
                () -> bankAccountDetailService.addBankAccountDetails(request));
    }

}