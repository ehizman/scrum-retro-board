package com.ehizman.scrumretroboard.service;

import com.ehizman.scrumretroboard.data.model.User;
import com.ehizman.scrumretroboard.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements  UserService{
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
}
