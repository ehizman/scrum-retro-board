package com.ehizman.scrumretroboard.config;

import com.ehizman.scrumretroboard.data.model.User;
import com.ehizman.scrumretroboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Component
public class DatabaseInit {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseInit(UserService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    @Transactional
    public void loadUsers(){
        if (userService.findUserByUsername("test-admin@gmail.com")==null){
            userService.save(new User(
                    null,
                    "test-admin@gmail.com",
                    passwordEncoder.encode("admin-password"),
                    "ROLE_ADMIN")
            );
        }
        if (userService.findUserByUsername("test-user@gmail.com")==null) {
            userService.save(new User(
                    null,
                    "test-user@gmail.com",
                    passwordEncoder.encode("password"),
                    "ROLE_USER")
            );
        }
    }
}
