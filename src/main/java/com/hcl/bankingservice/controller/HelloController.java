package com.hcl.bankingservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    String hello() {
        return  "<h1>Hello World</h1>";
    }
}
