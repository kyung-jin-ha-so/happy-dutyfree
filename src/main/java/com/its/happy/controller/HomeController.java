package com.its.happy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/main")
    public String main(){
        return "main";
    }

    @GetMapping("/myPageMain")
    public String myPageMain(){
        return "myPages/main";
    }

    @GetMapping("/adminMain")
    public String adminMain(){
        return "adminPages/main";
    }

}
