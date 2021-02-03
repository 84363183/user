package com.xinxing.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/docs")
    public String index(){
        return "forward:docs/index.html";
    }
}
