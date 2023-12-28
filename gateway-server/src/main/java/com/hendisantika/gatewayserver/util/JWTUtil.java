package com.hendisantika.gatewayserver.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-logging-filter2
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/28/23
 * Time: 09:05
 * To change this template use File | Settings | File Templates.
 */
@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;
}
