package com.hendisantika.gatewayserver.controller;

import com.hendisantika.gatewayserver.model.Company;
import com.hendisantika.gatewayserver.model.Student;
import com.hendisantika.gatewayserver.model.Type;
import com.hendisantika.gatewayserver.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-logging-filter2
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/28/23
 * Time: 09:23
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@RestController
@RequestMapping("/process")
@RequiredArgsConstructor
public class TypeController {

    private final RestTemplate restTemplate;

    private final AuthUtil authUtil;

    @PostMapping
    public String getType(@RequestBody Type type, ServerHttpRequest inRequest) {
        log.info("getting type");
        log.info("types:" + type.getTypes());
        type.getTypes().forEach(f -> {
            if (f.equals("Student")) {
                log.info("calling first microservice - student");
                HttpEntity<Student> request = new HttpEntity<>(
                        new Student(1, "Test", "Student"), setAuthHeader(inRequest.getHeaders().get("userName").toString(), inRequest.getHeaders().get("role").toString()));
                restTemplate.exchange("http://localhost:8080/first", HttpMethod.POST, request, String.class);
            }
            if (f.equals("Company")) {
                log.info("calling second microservice - company");
                HttpEntity<Company> request = new HttpEntity<>(
                        new Company(1, "Test", "Company"), setAuthHeader(inRequest.getHeaders().get("userName").toString(), inRequest.getHeaders().get("role").toString()));
                restTemplate.exchange("http://localhost:8080/second", HttpMethod.POST, request, String.class);
            }
        });
        return "done";
    }

    private HttpHeaders setAuthHeader(String userName, String role) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authUtil.getToken(userName, role));
        return headers;
    }
}
