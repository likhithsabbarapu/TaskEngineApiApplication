package com.likhith.taskengine.config;

import org.springframework.context.annotation.Configuration; // Marks this as a setup/config file
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allows our React App (Port 5173) to access our Java App (Port 8080)
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*") // Allows any header (standard for development)
                .allowCredentials(true); // Allows cookies/auth headers if we add them later
    }
}


/*
Request (React) -> Controller (JSON)

    Controller (Validation) -> Service (Logic)

    Service -> Repository (SQL)

    Repository -> Database (Storage) */