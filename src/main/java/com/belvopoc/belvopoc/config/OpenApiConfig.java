package com.belvopoc.belvopoc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI myOpenAPI() {

        Contact contact = new Contact();
        contact.setEmail("edvicati@gmail.com");
        contact.setName("Vicati");
        contact.setUrl("https://github.com/edvicaty/belvo-poc");


        Info info = new Info()
                .title("Belvo PoC API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage the Belvo API for retrieving accounts and transactions data");

        return new OpenAPI().info(info);
    }
}