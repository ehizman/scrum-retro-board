package com.ehizman.scrumretroboard;

import com.ehizman.scrumretroboard.data.repository.CommentRepository;
import com.ehizman.scrumretroboard.data.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootTest
@EnableJpaRepositories(basePackageClasses = {UserRepository.class, CommentRepository.class})
class ScrumRetroBoardApplicationTests {

    @Test
    void contextLoads() {
    }

}
