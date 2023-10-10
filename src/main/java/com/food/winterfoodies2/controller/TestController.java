package com.food.winterfoodies2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/main"})

public class TestController {
    @GetMapping({"/test"})
    public String Hello() {
        return "hello";// 18
    }
}
