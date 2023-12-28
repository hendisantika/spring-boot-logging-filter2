package com.hendisantika.gatewayserver.util;

import com.hendisantika.gatewayserver.model.Credential;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-logging-filter2
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/28/23
 * Time: 09:07
 * To change this template use File | Settings | File Templates.
 */
@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final RestTemplate restTemplate;

    public String getToken(String userName, String role) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("userName", userName);
        headers.set("role", role);
        HttpEntity<Credential> request = new HttpEntity<>(
                new Credential("naruto", "admin"), headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8088/login", HttpMethod.POST, request, String.class);
        System.out.println("token:" + response.getBody());
        return response.getBody();
    }
}
