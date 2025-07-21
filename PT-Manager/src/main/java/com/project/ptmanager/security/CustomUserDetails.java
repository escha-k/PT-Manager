package com.project.ptmanager.security;

import com.project.ptmanager.domain.member.Member;
import com.project.ptmanager.enums.MemberRole;
import java.util.Collection;
import java.util.List;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@EqualsAndHashCode
public class CustomUserDetails implements UserDetails {

  private final String username;
  private final String password;
  private final MemberRole role;

  public CustomUserDetails(Member member) {
    this.username = member.getUsername();
    this.password = member.getPassword();
    this.role = member.getRole();
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }
}
