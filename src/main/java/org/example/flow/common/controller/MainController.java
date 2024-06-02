package org.example.flow.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String toStaticPage() {
        return "forward:/index.html";
    }

    @GetMapping("/swagger")
    public String toSwaggerPage() {
        return "redirect:/swagger-ui.html";
    }

}
