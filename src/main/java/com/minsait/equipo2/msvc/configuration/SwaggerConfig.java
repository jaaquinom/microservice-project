package com.minsait.equipo2.msvc.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springOpenAPI(){
        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(new Info().title("Proyecto Equipo 2")
                        .description("Proveedor/Tienda/Cliente: Proyecto de Microservicios - Equipo 2")
                        .version("0.0.1-SNAPSHOP"))
                .externalDocs(new ExternalDocumentation()
                        .description("spingdoc-7")
                        .url("http://spingdoc.org"));
    }
}
