package com.example.demo.controller;

// import com.example.demo.entity.TestEntity;
import com.example.demo.service.TestService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
// import java.util.List;

@Controller
public class TestController {

    private final TestService service;

    public TestController(TestService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String welcomePage(Model model) {
        // model.addAttribute("liste", service.getAll());
        return "home";
    }
}
