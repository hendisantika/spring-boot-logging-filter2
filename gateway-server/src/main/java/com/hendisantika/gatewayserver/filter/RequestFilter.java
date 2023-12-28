package com.hendisantika.gatewayserver.filter;

import com.hendisantika.gatewayserver.model.Company;
import com.hendisantika.gatewayserver.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
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
 * Time: 09:21
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Component
public class RequestFilter implements GatewayFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Object body = exchange.getAttribute("cachedRequestBodyObject");
        log.info("in request filter");
        if (body instanceof Student) {
            log.info("body:" + body);
        } else if (body instanceof Company) {
            log.info("body:" + body);
        }
        return chain.filter(exchange);
    }
}
