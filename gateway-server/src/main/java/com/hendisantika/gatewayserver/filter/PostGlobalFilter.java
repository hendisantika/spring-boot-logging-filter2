package com.hendisantika.gatewayserver.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hendisantika.gatewayserver.model.Company;
import com.hendisantika.gatewayserver.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-logging-filter2
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/28/23
 * Time: 09:13
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
public class PostGlobalFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();
        DataBufferFactory dataBufferFactory = response.bufferFactory();
        ServerHttpResponseDecorator decoratedResponse = getDecoratedResponse(path, response, request, dataBufferFactory);
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    private ServerHttpResponseDecorator getDecoratedResponse(String path, ServerHttpResponse response, ServerHttpRequest request, DataBufferFactory dataBufferFactory) {
        return new ServerHttpResponseDecorator(response) {

            @Override
            public Mono<Void> writeWith(final Publisher<? extends DataBuffer> body) {

                if (body instanceof Flux<? extends DataBuffer> fluxBody) {

                    return super.writeWith(fluxBody.buffer().handle((dataBuffers, sink) -> {

                        DefaultDataBuffer joinedBuffers = new DefaultDataBufferFactory().join(dataBuffers);
                        byte[] content = new byte[joinedBuffers.readableByteCount()];
                        joinedBuffers.read(content);
                        String responseBody = new String(content, StandardCharsets.UTF_8);//MODIFY RESPONSE and Return the Modified response
                        log.info("requestId: " + request.getId() + ", method: " + request.getMethod() + ", req url: " + request.getURI() + ", response body :" + responseBody);
                        try {
                            if (request.getURI().getPath().equals("/first") && request.getMethod().equals("GET")) {
                                List<Student> student = new ObjectMapper().readValue(responseBody, List.class);
                                log.info("student:" + student);
                            } else if (request.getURI().getPath().equals("/second") && request.getMethod().equals("GET")) {
                                List<Company> companies = new ObjectMapper().readValue(responseBody, List.class);
                                log.info("companies:" + companies);
                            }
                        } catch (JsonProcessingException e) {
                            sink.error(new RuntimeException(e));
                            return;
                        }
                        sink.next(dataBufferFactory.wrap(responseBody.getBytes()));
                    })).onErrorResume(err -> {

                        log.info("error while decorating Response: {}" + err.getMessage());
                        return Mono.empty();
                    });

                }
                return super.writeWith(body);
            }
        };
    }
}
