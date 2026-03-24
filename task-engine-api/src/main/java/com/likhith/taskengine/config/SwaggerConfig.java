package com.likhith.taskengine.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SwaggerConfig: Configuration for OpenAPI/Swagger documentation
 * Auto-generates interactive API documentation
 * Access at: http://localhost:8080/swagger-ui.html
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Enterprise Task System API")
                .description("A comprehensive REST API for managing tasks with pagination, filtering, and role-based access control")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Likhith Sabbarapu")
                    .email("likhith@example.com")
                    .url("https://github.com/likhith"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}

