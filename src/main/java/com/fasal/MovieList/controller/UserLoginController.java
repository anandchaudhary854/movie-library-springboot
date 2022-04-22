package com.fasal.MovieList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserLoginController {



    @GetMapping("/login")
    public String login(){
        return "login";
    }



}
