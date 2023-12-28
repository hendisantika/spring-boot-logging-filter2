package com.hendisantika.secondservice.controller;

import com.hendisantika.secondservice.model.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-logging-filter2
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/28/23
 * Time: 08:42
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@RestController
@RequestMapping("/second")
public class FirstController {
    @GetMapping
    public List<Company> getCompanies() {
        log.info("Getting Companies ...");
        return Arrays.asList(
                new Company(1, "TechM", "company"),
                new Company(2, "TCS", "company"),
                new Company(2, "META", "company"),
                new Company(2, "Apple", "company"),
                new Company(2, "Microsoft", "company"),
                new Company(2, "Alphabet", "company")
        );
    }
}
