package com.todolist.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.todolist.models.user.User;
import com.todolist.repositories.TaskRepository;
import com.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class JwtTokenService {
    @Value("${api.jwt.secret}")
    private String secret;

    @Autowired
    public UserRepository userRepository;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("api-backend-todolist")
                    .withSubject(user.getUsername())
                    .withExpiresAt(generateExpirationTime())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generating token", e);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("api-backend-todolist")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }
    }

    public User getUserByToken(String token) {
        token = token.replace("Bearer ", "");
        String username = this.validateToken(token);

        return userRepository.findUserByUsername(username);
    }

    public static Date generateExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 1);

        return calendar.getTime();
    }
}
