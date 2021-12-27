package com.ehizman.scrumretroboard.service;

import com.ehizman.scrumretroboard.data.model.User;
import com.ehizman.scrumretroboard.data.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testCanCreateUser(){
        User user = new User();
        user.setUserName("testUser");
        user.setPassword("password");
        user.setRole("ROLE_USER");

        when(userRepository.save(user)).thenReturn(user);
        userService.create(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testCanLoadUserByUsername(){
        User user = new User();
        user.setUserName("testUser");
        user.setPassword("password");
        user.setRole("ROLE_USER");

        when(userRepository.findByUserName("testUser")).thenReturn(Optional.of(user));
        UserDetails actual  = userService.loadUserByUsername("testUser");
        verify(userRepository, times(1)).findByUserName("testUser");
        assertThat(actual).hasFieldOrPropertyWithValue("username", "testUser");
        assertThat(actual).hasFieldOrPropertyWithValue("password", "password");
        assertThat(actual).hasFieldOrPropertyWithValue("authorities", Set.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

}
