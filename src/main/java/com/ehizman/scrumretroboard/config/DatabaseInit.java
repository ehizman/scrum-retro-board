package com.ehizman.scrumretroboard.config;

import com.ehizman.scrumretroboard.data.model.User;
import com.ehizman.scrumretroboard.data.repository.UserRepository;
import com.ehizman.scrumretroboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Component
public class DatabaseInit {
    private UserService userService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseInit(UserRepository userRepository, UserService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userService = userDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    @Transactional
    public void loadUsers(){
        User user1 = userService.create(new User(
                null,
                "Ehis Edemakhiota",
                passwordEncoder.encode("password"),
                "ROLE_USER")
        );

        User user2 = userService.create(new User(
                null,
                "Nosa Edemakhiota",
                passwordEncoder.encode("password"),
                "ROLE_USER")
        );
    }
}
