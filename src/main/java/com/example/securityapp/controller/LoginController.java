package com.example.securityapp.controller;

import com.example.securityapp.dto.LoginForm;
import com.example.securityapp.dto.User;
import com.example.securityapp.dto.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Slf4j
@Controller
public class LoginController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/loginForm")
    public String login(LoginForm loginForm) {
        return "loginForm";
    }

    @GetMapping("/loginFailure")
    public String loginFailure() {
        return "loginFailure";
    }


    @GetMapping("/loginSuccess")
    public String loginSuccess() {
        return "loginSuccess";
    }


    @GetMapping("/logoutSuccess")
    public String logout() {
        log.info("call logout");
        return "redirect:/loginForm";
    }

    @GetMapping("/users/testManager")
    @ResponseBody
    public String manager() {
        return "Hello, Manager";
    }


    @GetMapping("/users/testAdmin")
    @ResponseBody
    public String admin() {
        return "Hello, Admin!!";
    }


    @GetMapping("/hello")
    public String hello(@AuthenticationPrincipal UserAccount userAccount) {
        User user = userAccount.getUser();
        log.info("User : {}", user);
        return "hello";
    }

}






