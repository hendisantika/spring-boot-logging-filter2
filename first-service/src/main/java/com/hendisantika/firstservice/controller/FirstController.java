package com.hendisantika.firstservice.controller;

import com.hendisantika.firstservice.model.Student;
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
 * Time: 08:34
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@RestController
@RequestMapping("/first")
public class FirstController {
    @GetMapping
    public List<Student> getStudent() {
        log.info("Getting students ....");
        return Arrays.asList(
                new Student(1, "Itadori Yuuji", "Student"),
                new Student(2, "Megumi Zenin", "Student"),
                new Student(2, "Yuki Okkutsu", "Student"));
    }
}
