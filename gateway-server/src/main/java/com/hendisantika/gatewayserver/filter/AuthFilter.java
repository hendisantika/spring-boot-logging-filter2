package com.hendisantika.gatewayserver.filter;

import com.hendisantika.gatewayserver.util.AuthUtil;
import com.hendisantika.gatewayserver.util.JWTUtil;
import com.hendisantika.gatewayserver.validator.RouteValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-logging-filter2
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/28/23
 * Time: 09:08
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Component
@RefreshScope
@RequiredArgsConstructor
public class AuthFilter implements GatewayFilter {

    private final RouteValidator routeValidator;

    private final JWTUtil jwtUtil;

    private final AuthUtil authUtil;

    @Value("${authentication.enabled}")
    private boolean authEnabled;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!authEnabled) {
            log.info("Authentication is disabled. To enable it, make \"authentication.enabled\" property as true");
            return chain.filter(exchange);
        }
        String token = "";
        ServerHttpRequest request = exchange.getRequest();

        if (routeValidator.isSecured.test(request)) {
            log.info("validating authentication token");
            if (this.isCredsMissing(request)) {
                log.info("in error");
                return this.onError(exchange, "Credentials missing", HttpStatus.UNAUTHORIZED);
            }
            if (request.getHeaders().containsKey("userName") && request.getHeaders().containsKey("role")) {
                token = authUtil.getToken(request.getHeaders().get("userName").toString(), request.getHeaders().get("role").toString());
            } else {
                token = request.getHeaders().get("Authorization").toString().split(" ")[1];
            }

            if (jwtUtil.isInvalid(token)) {
                return this.onError(exchange, "Auth header invalid", HttpStatus.UNAUTHORIZED);
            } else {
                log.info("Authentication is successful");
            }

            this.populateRequestWithHeaders(exchange, token);
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

}
