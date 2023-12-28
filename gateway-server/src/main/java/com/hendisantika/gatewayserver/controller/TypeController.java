package com.hendisantika.gatewayserver.controller;

import com.hendisantika.gatewayserver.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
