package com.dmdev.spring.http.controller;

import com.dmdev.spring.dto.UserLoginDto;
import com.dmdev.spring.dto.UserReadDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

//    PostMapping on "login" provided by Spring Security
//    @PostMapping("/login")
//    public String login(@ModelAttribute UserLoginDto userLoginDto,
//                        Model model) {
//        return "redirect:/hello";
//    }
}
