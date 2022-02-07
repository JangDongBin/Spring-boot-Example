package com.sample.sample.controller;


import com.sample.sample.entity.testEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/test")
public class testController {
    
    @GetMapping("/test1")
    public String testpage1() {
        return "test1";
    }

    @GetMapping("/test2")
    public String testpage2(Model model, @RequestParam(required = false) String param1, @ModelAttribute("testEntity") testEntity testEntity) {
        model.addAttribute("thymeleafparam1", param1);
        return "test2";
    }
    
    @PostMapping("/test3")
    public String testpage3(Model model, testEntity entity) {
        model.addAttribute("thymeleafEntity", entity);
        return "test3";
    }
}