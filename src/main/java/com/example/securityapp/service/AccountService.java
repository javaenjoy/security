package com.example.securityapp.service;

import com.example.securityapp.dto.*;
import com.example.securityapp.dto.User;
import com.example.securityapp.mapper.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import org.springframework.util.Assert;

import java.util.*;

@Slf4j
@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRolesMapper userRolesMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("1. AccountService loadUserByUsername {}", username);
        return getLoginUser(username);
    }

    protected UserDetails getLoginUser(String email) throws UsernameNotFoundException {

        User user = null;
        List<GrantedAuthority> authorityList = null;

        try {
            user = userMapper.findByEmail(email);

            log.info("2. AccountService getLoginUser User {}", user);

            if (user == null) {
                throw new UsernameNotFoundException("no user found [email=" + email + "]");
            }

            List<UserRole>  userRoles = userRolesMapper.findRolesByEmail(email);

            List<String> roles = new ArrayList<>();
            for (UserRole userRole : userRoles) {
                roles.add(userRole.getRoleName().replace("ROLE_",""));
            }


            log.info("=======================================================");
            log.info("3. User Roles : {}", userRoles);
            log.info("=======================================================");


            List<GrantedAuthority> authorities = new ArrayList(roles.size());

            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }

            return new UserAccount(user, authorities);

            /*
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(roles.toArray(new String[roles.size()])) //List<SimpleGrantedAuthority>
                    .build();
            */

        } catch(Exception ex) {
            log.error("failed to get LoginUser.", ex);

            if(ex instanceof UsernameNotFoundException) {
                log.error("failed to get LoginUser.", ex);
                UsernameNotFoundException e = (UsernameNotFoundException)ex;
                throw e;
            }
            throw new UsernameNotFoundException("could not select user.", ex);
        }
    }

}
