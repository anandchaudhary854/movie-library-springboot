package com.fasal.MovieList.service;


import com.fasal.MovieList.dto.UserRegistrationDto;
import com.fasal.MovieList.model.UserModel;
import com.fasal.MovieList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void save(UserModel userModel){
        this.userRepository.save(userModel);
    }


    public UserModel save(UserRegistrationDto registrationDto) {

        UserModel user = new UserModel(registrationDto.getFirstName(),
                registrationDto.getLastName(),
                registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username);
    }
}
