package com.sparta.assignment.nbcampspringtodo.security;

import com.sparta.assignment.nbcampspringtodo.feature.user.User;
import java.io.Serial;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public final class UserDetailsImpl implements UserDetails {

  @Serial
  private static final long serialVersionUID = 0L;
  private final User user;

  private final Set<GrantedAuthority> authorities = new HashSet<>();


  public UserDetailsImpl(User user) {
    this.user = user;
    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (UserDetailsImpl) obj;
    return Objects.equals(this.user, that.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user);
  }

  @Override
  public String toString() {
    return "UserDetailsImpl[" + "user=" + user + ']';
  }

}
