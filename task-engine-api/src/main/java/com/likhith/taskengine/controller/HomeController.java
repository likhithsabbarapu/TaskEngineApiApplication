package com.likhith.taskengine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HomeController: Handles root URL and login requests
 */
@Controller
public class HomeController {
    
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    
    // Default credentials
    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "admin123";
    
    /**
     * Display login page at root URL
     */
    @GetMapping("/")
    public String showLoginPage() {
        logger.info("User accessing root URL - showing login page");
        return "login";
    }
    
    /**
     * Handle login form submission
     */
    @PostMapping("/login")
    public RedirectView handleLogin(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            Model model) {
        
        logger.info("Login attempt with username: {}", username);
        
        // Simple authentication check
        if (DEFAULT_USERNAME.equals(username) && DEFAULT_PASSWORD.equals(password)) {
            logger.info("Login successful for user: {}", username);
            // Redirect to Swagger UI on successful login
            return new RedirectView("/swagger-ui.html");
        } else {
            logger.warn("Login failed for user: {}", username);
            // Redirect back to login page with error
            return new RedirectView("/?error=invalid");
        }
    }
    
    /**
     * API documentation redirect
     */
    @GetMapping("/api-docs")
    public RedirectView redirectToDocs() {
        return new RedirectView("/swagger-ui.html");
    }
}

