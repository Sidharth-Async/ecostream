package com.ecostream.user.controller;

import com.ecostream.user.dto.JwtAuthResponse;
import com.ecostream.user.dto.Signin;
import com.ecostream.user.dto.Signup;
import com.ecostream.user.model.UserEntity;
import com.ecostream.user.repo.UserRepository;
import com.ecostream.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class user_Controller {

    private final UserRepository repository;
    private final UserService service;

    public user_Controller(UserRepository repository, UserService service){
        this.repository = repository;
        this.service = service;
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<Signup> createUser(@RequestBody Signup signup){
        Signup savedUser = service.register(signup);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/auth/signup")
    public List<UserEntity> getAllUsers(){
        return service.getAllUsers();
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<?> login(@RequestBody Signin signin) {
        try {
            // ONE LINE: Call the method above
            String token = service.verify(signin);

            // Return the response
            return ResponseEntity.ok(new JwtAuthResponse(token));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}
