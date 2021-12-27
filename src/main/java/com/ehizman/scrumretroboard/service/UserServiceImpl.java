package com.ehizman.scrumretroboard.service;

import com.ehizman.scrumretroboard.data.model.User;
import com.ehizman.scrumretroboard.data.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements  UserService, UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username).orElseThrow(()-> new UsernameNotFoundException("user not found!"));
        log.info("User --> {}", user.toString());
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole())));
    }
}
