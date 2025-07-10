package com.example.java.Security.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.Entity.User.User;
import com.example.java.Repository.User.UserRepository;
import com.example.java.Security.DTO.AuthRequest;
import com.example.java.Security.DTO.AuthResponse;
import com.example.java.Security.JWT.JwtUtil;


@RestController
@RequestMapping("/auth")
public class AuthController {

     @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
     
   @GetMapping("/testing")
    public ResponseEntity<String> test() {
       System.out.println("The request is coming to the test endpoint");
        
        return ResponseEntity.ok("The Application is running......");
    }
    
    //this will be used for the login of the user 
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        System.out.println("The request for login is coming.......");
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getUserId(), req.getPassword()));

        String token = jwtUtil.generateToken(req.getUserId());
        return ResponseEntity.ok(new AuthResponse(token));
    }
    
   
    //this will  will be used  for the signup and saving the user  who is coming for the  first time
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        

     //fetching the user by userId and check that  the  user is already  there or not
    Optional<User> existingUser = userRepository.findByUserId(user.getUserId());

    if (existingUser.isPresent()) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("User with this mobile number already exists.");
    }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    

}
