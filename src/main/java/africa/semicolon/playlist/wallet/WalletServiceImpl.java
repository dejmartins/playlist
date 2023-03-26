package africa.semicolon.playlist.wallet;

import africa.semicolon.playlist.exception.userExceptions.UserAlreadyExistsException;
import africa.semicolon.playlist.user.models.User;
import africa.semicolon.playlist.user.repositories.UserRepository;
import africa.semicolon.playlist.wallet.dtos.requests.DepositRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService{

    private final UserRepository userRepository;

    @Override
    public void deposit(DepositRequest depositRequest) {
        Optional<User> user = userRepository.findUserByEmailAddress(depositRequest.getEmailAddress());

        if(user.isPresent()){
            addToUserBalance(depositRequest.getAmount(), user.get());
        } else {
            throw new UserAlreadyExistsException();
        }
    }

    private void addToUserBalance(BigDecimal amount, User user) {
        Wallet wallet = user.getWallet();
        wallet.deposit(amount);
        userRepository.save(user);
    }

    @Override
    public void withdraw(BigDecimal amount) {

    }
}
