package com.jobtracker.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.tags.Tag;
import java.util.List;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .servers(List.of(
                new Server().url("/api").description("API Base URL")
            ))
            .info(new Info()
                .title("JobTracker API")
                .version("1.0")
                .description("JobTracker Application API Documentation")
            )
            .addTagsItem(new Tag().name("User").description("User management endpoints"));
    }
}