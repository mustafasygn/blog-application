package com.blog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Blog Application API",
        version = "1.0.0",
        description = "API documentation for the Blog Application"
))
public class OpenApiConfig {
} 