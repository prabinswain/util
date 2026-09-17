package com.auth_util.auth.api.security;

import com.auth_util.auth.api.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class JwtUser implements UserDetails {

    private static final long serialVersionUID = 7526472295622776147L;  // unique id


    private User user;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;

    private Set<String> userGroup;

    public JwtUser(User user, Collection<? extends GrantedAuthority> authorities,
                   boolean enabled, Set<String> userGroup) {
        this.user = user;
        this.authorities = authorities;
        this.enabled = enabled;
        this.userGroup = userGroup;
    }

    public User getDbUser() {
        return user;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUser_id();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Set<String> getUserGroup() {
        return userGroup;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
