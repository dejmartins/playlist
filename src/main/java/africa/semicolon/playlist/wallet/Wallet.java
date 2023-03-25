package africa.semicolon.playlist.wallet;

import africa.semicolon.playlist.user.models.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal balance;

    public void deposit(BigDecimal amount){
        balance = balance.add(amount);
    }
}
