package com.ehizman.scrumretroboard.service;

import com.ehizman.scrumretroboard.data.model.User;

public interface UserService {
    User save(User user);
    User findUserByUsername(String userName);
    User createUser(String userName, String password, String role);
}
