package com.hendisantika.gatewayserver.config;

import com.hendisantika.gatewayserver.filter.AuthFilter;
import com.hendisantika.gatewayserver.model.Company;
import com.hendisantika.gatewayserver.model.Student;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.RequestFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.WebFilter;

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

    @Bean
    public WebFilter responseFilter() {
        return new PostGlobalFilter();
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        // adding 2 rotes to first microservice as we need to log request body if method is POST
        return builder.routes()
                .route("first-microservice", r -> r.path("/first")
                        .and().method("POST")
                        .and().readBody(Student.class, s -> true).filters(f -> f.filters(requestFilter, authFilter))
                        .uri("http://localhost:8081"))
                .route("first-microservice", r -> r.path("/first")
                        .and().method("GET").filters(f -> f.filters(authFilter))
                        .uri("http://localhost:8081"))
                .route("second-microservice", r -> r.path("/second")
                        .and().method("POST")
                        .and().readBody(Company.class, s -> true).filters(f -> f.filters(requestFilter, authFilter))
                        .uri("http://localhost:8082"))

                .route("second-microservice", r -> r.path("/second")
                        .and().method("GET").filters(f -> f.filters(authFilter))
                        .uri("http://localhost:8082"))
                .route("auth-server", r -> r.path("/login")
                        .uri("http://localhost:8088"))
                .build();
    }
}
