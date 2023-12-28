package com.hendisantika.gatewayserver.config;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.RequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-logging-filter2
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/28/23
 * Time: 08:58
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final RequestFilter requestFilter;

    private final AuthFilter authFilter;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
