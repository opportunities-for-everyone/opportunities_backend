package com.project.opportunities.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("O4E-Backend API")
                        .version("1.0.final")
                        .description("""
                                API documentation for charitable foundation
                                 O4E(Opportunities for Everyone) Backend API
                                """)
                        .contact(new Contact()
                                .name("Ruslan Shastkiv")
                                .email("shastkiv.ruslan.dev@gmail.com")
                                .url("https://github.com/ShastkivRuslan"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .addServersItem(new Server()
                        .description("Prod ENV")
                        .url("https://backend-api.space/api"))
                .addServersItem(new Server()
                        .description("Local ENV")
                        .url("http://localhost:8089/api"));
    }
}
