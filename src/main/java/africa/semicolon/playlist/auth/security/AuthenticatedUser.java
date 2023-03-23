package africa.semicolon.playlist.auth.security;

import africa.semicolon.playlist.user.data.models.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class AuthenticatedUser implements UserDetails {

    private User user;

    private List<String> roles;

    public String getUsername() {
        return user.getEmailAddress();
    }

    public String getPassword() {
        return user.getPassword();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return mapRolesToAuthorities();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities() {
        return roles
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
