package com.stockmarket.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Simple controller to provide basic information about the application.
 * This can be used to verify that the application is running correctly.
 */
@RestController
@Tag(name = "Welcome", description = "Basic application information endpoints")
public class WelcomeController {

    @Value("${spring.application.name:Stock Market Application}")
    private String applicationName;

    private final Environment environment;

    public WelcomeController(Environment environment) {
        this.environment = environment;
    }

    /**
     * Simple welcome endpoint that returns basic application information.
     * 
     * @return Map containing application information
     */
    @GetMapping("/")
    @Operation(summary = "Get application information", 
               description = "Returns basic information about the application including name and active profiles")
    public Map<String, Object> welcome() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", applicationName);
        response.put("status", "UP");
        response.put("profiles", Arrays.asList(environment.getActiveProfiles()));
        response.put("message", "Welcome to the Stock Market API");
        response.put("swagger", "/swagger-ui.html");
        return response;
    }
} 