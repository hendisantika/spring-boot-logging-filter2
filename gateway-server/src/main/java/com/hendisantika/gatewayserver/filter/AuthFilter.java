package com.hendisantika.gatewayserver.filter;

import com.hendisantika.gatewayserver.util.AuthUtil;
import com.hendisantika.gatewayserver.util.JWTUtil;
import com.hendisantika.gatewayserver.validator.RouteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.stereotype.Component;

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
@Component
@RefreshScope
@RequiredArgsConstructor
public class AuthFilter implements GatewayFilter {

    private final RouteValidator routeValidator;

    private final JWTUtil jwtUtil;

    private final AuthUtil authUtil;

    @Value("${authentication.enabled}")
    private boolean authEnabled;
}
