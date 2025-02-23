package com.ren.spring_security_jwt.service;
 
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ren.spring_security_jwt.model.User;
import com.ren.spring_security_jwt.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Create User
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Get All Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get User by ID
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    // Update User
    public User updateUser(String id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
  
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }).orElse(null);
    }

    // Delete User
    public boolean deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
