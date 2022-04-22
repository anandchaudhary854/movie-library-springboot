package com.fasal.MovieList.controller;


import com.fasal.MovieList.dto.UserRegistrationDto;
import com.fasal.MovieList.service.MyUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    private MyUserDetailsService myUserDetailsService;

    public UserRegistrationController(MyUserDetailsService myUserDetailsService) {
        super();
        this.myUserDetailsService = myUserDetailsService;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(){
        return "registration";
    }


    @PostMapping
    public String registerUserAccount(@ModelAttribute("user")UserRegistrationDto registrationDto){
        try{
            myUserDetailsService.save(registrationDto);
        }catch (Exception e){
            return "redirect:/registration?error";
        }
        return "redirect:/registration?success";
    }
}
