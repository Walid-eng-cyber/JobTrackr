// src/main/java/com/jobtracker/backend/controller/SwaggerController.java
package com.jobtracker.backend.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Hidden
public class SwaggerController {
    
    @RequestMapping("/api-docs")
    public String redirectToSwagger() {
        return "redirect:/swagger-ui.html";
    }
}