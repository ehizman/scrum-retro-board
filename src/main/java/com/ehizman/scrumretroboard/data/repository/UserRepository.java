package com.ehizman.scrumretroboard.data.repository;

import com.ehizman.scrumretroboard.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}
