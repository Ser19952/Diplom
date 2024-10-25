package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@org.springframework.stereotype.Controller
public class Controller {

    @RequestMapping("/hello")
    public String hello () {
        return "contact";

    }
}
