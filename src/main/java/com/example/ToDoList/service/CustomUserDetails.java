package com.example.ToDoList.service;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;


public class CustomUserDetails implements UserDetails {
    private String name;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private String email;
    public CustomUserDetails(String name, String password, Collection<? extends GrantedAuthority> authorities, String email) {
        this.name = name;
        this.password = password;
        this.authorities = authorities;
        this.email = email;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return name;
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
}
