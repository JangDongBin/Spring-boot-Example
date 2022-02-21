package com.sample.sample.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/security")
public class SecurityController {
    @GetMapping("/test1")
    public String Test1(){
        return "test/test1";
    }

    @GetMapping("/test2")
    public String Test2(){
        return "test/test2";
    }
}
