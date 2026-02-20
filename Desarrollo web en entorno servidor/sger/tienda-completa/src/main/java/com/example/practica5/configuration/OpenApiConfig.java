package com.example.practica5.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI miProyectoOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("API")
                        .description("Backend de la gestion de una tienda")
                        .version("v0.0.1"))
                .externalDocs(new ExternalDocumentation()
                        .description("Web de GitHub")
                        .url("https://ivan191000.github.io/Cuaderno_DIW/"));
    }
}
