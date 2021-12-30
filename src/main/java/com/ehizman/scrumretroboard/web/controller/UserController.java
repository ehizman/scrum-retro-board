package com.ehizman.scrumretroboard.web.controller;

import com.ehizman.scrumretroboard.data.model.User;
import com.ehizman.scrumretroboard.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

@Slf4j
@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/user-registration")
    public String registerUser(Model model,
                               @RequestParam(name = "username") String userName,
                               @RequestParam(name = "password") String password,
                               @RequestParam(name = "role") String role){

        @NotNull User user = userService.createUser(userName, password, role);
        userService.save(user);
        model.addAttribute("user", user);
        return "redirect:/login";
    }
}
