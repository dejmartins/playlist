package africa.semicolon.playlist.user.models;

import africa.semicolon.playlist.wallet.Wallet;
import africa.semicolon.playlist.wallet.bankAccountDetails.BankAccountDetail;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    @Column(unique = true, nullable = false)
    private String emailAddress;
    private final LocalDateTime dateJoined = LocalDateTime.now();
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name="wallet_id", referencedColumnName="id")
    private Wallet wallet;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bankAccountDetails_id")
    private List<BankAccountDetail> bankAccountDetails;

    public void addBankAccountDetails(BankAccountDetail bankAccountDetail){
        bankAccountDetails.add(bankAccountDetail);
    }

    public void removeBankAccountDetails(BankAccountDetail bankAccountDetail){
        if(bankAccountDetails != null) bankAccountDetails.remove(bankAccountDetail);
    }
}
