package com.hendisantika.authenticationserver.controller;

import com.hendisantika.authenticationserver.model.Credential;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-logging-filter2
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/28/23
 * Time: 08:52
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class AuthController {

    @Value("${jwt.secret}")
    private String secret;

    @GetMapping
    public String getToken() {
        log.info("inside auth login");
        return Jwts.builder()
                .claim("id", "ankitha")
                .claim("role", "admin")
                .setSubject("Test Token")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(10, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    @PostMapping
    public String getTokenProvidingUsernameAndPassword(@RequestBody Credential credential) {
        log.info("inside getTokenProvidingUsernameAndPassword");
        return Jwts.builder()
                .claim("id", credential.getUserName())
                .claim("role", credential.getRole())
                .setSubject("Test Token")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(10, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }
}
