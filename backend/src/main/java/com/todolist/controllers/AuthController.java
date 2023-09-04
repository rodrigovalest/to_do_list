package com.todolist.controllers;

import com.todolist.config.security.JwtTokenService;
import com.todolist.models.user.User;
import com.todolist.models.user.UserDTO;
import com.todolist.models.user.UserRole;
import com.todolist.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/validate")
    public ResponseEntity<?> testAuthentication(
            @RequestHeader(value = "Authorization", required = false) String token
    ) throws Exception {
        Map<String, Object> response = new HashMap<>();

        token = token.replace("Bearer ", "");

        String usernameReturnedByTokenValidation = jwtTokenService.validateToken(token);

        if (userRepository.findByUsername(usernameReturnedByTokenValidation) != null) {
            response.put("message", "User authorized");
            return ResponseEntity.ok().body(response);
        }

        response.put("message", "User unauthorized");
        return ResponseEntity.badRequest().body(response);
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signUp(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult bindingResult
    ) throws Exception {
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            response.put("message", "Invalid request body");
            return ResponseEntity.badRequest().body(response);
        }

        if (userRepository.existsByUsername(userDTO.getUsername())) {
            response.put("message", "This user already exists");
            return ResponseEntity.badRequest().body(response);
        }

        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        newUser.setRole(UserRole.ROLE_USER);

        userRepository.save(newUser);

        response.put("message", "Sucess on create user");
        return ResponseEntity.ok().body(response);
    }


    @PostMapping("/signin")
    public ResponseEntity<?> signIn(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult bindingResult
    ) throws Exception {
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            response.put("message", "Invalid request body");
            return ResponseEntity.badRequest().body(response);
        }

        var authentication = new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
        var auth = authenticationManager.authenticate(authentication);
        var token = jwtTokenService.generateToken((User) auth.getPrincipal());

        response.put("token", token);
        response.put("message", "Sucess on authentication");
        return ResponseEntity.ok().body(response);
    }
}
