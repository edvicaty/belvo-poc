package com.vicati.vicati.config;

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
        contact.setUrl("https://github.com/edvicaty");


        Info info = new Info()
                .title("Vicati API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage the Vicati API");

        return new OpenAPI().info(info);
    }
}