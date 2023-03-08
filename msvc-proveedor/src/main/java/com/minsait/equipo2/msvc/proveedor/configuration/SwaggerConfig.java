package com.minsait.equipo2.msvc.proveedor.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public static OpenAPI springOpenAPI(){
        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(new Info().title("Proveedor")
                        .description("Proyecto Msvc Equipo 2")
                        .version("0.0.1-SNAPSHOT"))
                .externalDocs(new ExternalDocumentation()
                        .description("springdoc-openapi")
                        .url("http://springdoc.org"));
    }
}
