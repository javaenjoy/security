package com.example.securityapp.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


@Getter
@Setter
public class UserAccount extends org.springframework.security.core.userdetails.User {

    private com.example.securityapp.dto.User user;

    public UserAccount(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);
        this.user = user;
    }

}
