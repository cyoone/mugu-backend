package com.example.mugu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    // 기본페이지 요청 메서드
    @RequestMapping("/index")
    @GetMapping(value = "/")
    public String index() {
        return "index"; // -> templates 폴더의 index.html을 찾아감
    }
}