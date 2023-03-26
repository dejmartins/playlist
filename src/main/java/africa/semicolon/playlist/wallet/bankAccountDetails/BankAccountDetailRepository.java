package africa.semicolon.playlist.wallet.bankAccountDetails;

import africa.semicolon.playlist.wallet.bankAccountDetails.dtos.responses.BankAccountDetailsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountDetailRepository extends JpaRepository<BankAccountDetail, Long> {

    void deleteBankAccountDetailByAccountNumber(String accountNumber);
    Optional<BankAccountDetail> findBankAccountDetailByAccountNumber(String accountNumber);
}
