package com.ren.spring_security_jwt.service;
  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ren.spring_security_jwt.config.JwtUtil;
import com.ren.spring_security_jwt.model.User;
import com.ren.spring_security_jwt.repository.UserRepository;

import java.util.Optional; 

@Service
public class AuthenticationService {

    private UserRepository userRepository;
 
    private JwtUtil jwtUtil;
 
    private PasswordEncoder passwordEncoder;
    // 🔥 Use Constructor Injection
    public AuthenticationService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }
    public String registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return jwtUtil.generateToken(user);
    }

    public String authenticateUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return jwtUtil.generateToken(userOpt.get());
        }
        return null;
    }
}
