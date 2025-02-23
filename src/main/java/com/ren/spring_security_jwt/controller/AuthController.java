package com.ren.spring_security_jwt.controller;  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ren.spring_security_jwt.model.User;
import com.ren.spring_security_jwt.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private Logger log= LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationService  authService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return authService.registerUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestParam(name="email") String email, @RequestParam(name="password") String password) {
        
        log.debug("email : {}, password : {} ",email,password);
        return authService.authenticateUser(email, password);
    }
}
