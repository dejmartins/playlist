package africa.semicolon.playlist.wallet.bankAccountDetails;

import africa.semicolon.playlist.user.data.models.User;
import africa.semicolon.playlist.user.data.models.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String bankCode;
    @NotBlank
    private String bankName;
    @NotBlank
    private String accountName;
    @NotBlank
    private String accountNumber;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
}
