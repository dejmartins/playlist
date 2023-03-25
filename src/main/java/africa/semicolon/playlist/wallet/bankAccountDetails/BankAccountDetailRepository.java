package africa.semicolon.playlist.wallet.bankAccountDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountDetailRepository extends JpaRepository<BankAccountDetail, Long> {
}
