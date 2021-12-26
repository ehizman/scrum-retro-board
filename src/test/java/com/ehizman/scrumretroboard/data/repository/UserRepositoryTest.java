package com.ehizman.scrumretroboard.data.repository;

import com.ehizman.scrumretroboard.data.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Slf4j
public class UserRepositoryTest {
    private TestEntityManager testEntityManager;
    private UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(TestEntityManager testEntityManager, UserRepository userRepository) {
        this.testEntityManager = testEntityManager;
        this.userRepository = userRepository;
    }

    @Test
    void findByUsername(){
        User user = new User();
        user.setUserName("Mary Doe");
        user.setPassword("password");
        user.setRole("USER");
        testEntityManager.persist(user);
        testEntityManager.flush();

        User actual = userRepository.findByUserName("Mary Doe").orElseThrow(()-> new UsernameNotFoundException("user not found!"));
        assertThat(actual).isEqualTo(user);
    }

    @Test
    void testCanSaveUser(){
        User user = new User();
        user.setUserName("Mary Doe");
        user.setPassword("password");
        user.setRole("USER");

        User actual = userRepository.save(user);

        assertThat(actual).isNotNull();
        assertThat(user.getId()).isNotNull();
        log.info("User id -> {}",user.getId());
    }
}
