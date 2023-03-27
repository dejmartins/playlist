package africa.semicolon.playlist.wallet.services;

import africa.semicolon.playlist.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final UserRepository userRepository;

    @Override
    public void withdraw(BigDecimal amount) {

    }
}
