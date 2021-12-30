package com.ehizman.scrumretroboard.controller;

import com.ehizman.scrumretroboard.data.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @PostMapping("/registeration")
    public String registerUser(@ModelAttribute User user, Model model){
        model.addAttribute("user", user);
        return "login";
    }
}
