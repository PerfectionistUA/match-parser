package com.example.match_parser.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI matchParserOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Match Parser API")
                        .description("REST API for importing and storing match results")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Yaroslav")
                                .email("yaroslav.tsv@gmail.com"))
                        .license(new License()
                                .name("Educational Project")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project documentation"));
    }
}
