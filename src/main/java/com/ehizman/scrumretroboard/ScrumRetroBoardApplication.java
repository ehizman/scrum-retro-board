package com.ehizman.scrumretroboard;

import com.ehizman.scrumretroboard.data.repository.CommentRepository;
import com.ehizman.scrumretroboard.data.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = {UserRepository.class, CommentRepository.class})
public class ScrumRetroBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScrumRetroBoardApplication.class, args);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
}
