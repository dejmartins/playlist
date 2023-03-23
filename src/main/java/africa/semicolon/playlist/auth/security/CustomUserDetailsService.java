package africa.semicolon.playlist.auth.security;

import africa.semicolon.playlist.user.data.models.User;
import africa.semicolon.playlist.user.data.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =
                userRepository.findByEmailAddress(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return AuthenticatedUser.builder()
                .user(user)
                //TODO: Add user roles
                .roles(List.of("USER_ROLE"))
                .build();
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<String> roles) {
        return roles
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
