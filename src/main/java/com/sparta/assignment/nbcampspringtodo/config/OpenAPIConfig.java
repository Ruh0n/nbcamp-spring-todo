package com.sparta.assignment.nbcampspringtodo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class OpenAPIConfig implements WebMvcConfigurer {

  private static final String JWT_AUTH = "jwtAuth";


  @Bean
  public OpenAPI openAPI() {

    return new OpenAPI().info(info())
        .addServersItem(server())
        .addSecurityItem(securityRequirement())
        .components(components());
  }

  private Server server() {
    Server server = new Server();
    server.setUrl("https://localhost:8080/");
    server.setDescription("Local Server");

    return server;
  }

  private Info info() {
    return new Info().title("Todo API").version("1.0").description("Todo API");
  }

  private SecurityRequirement securityRequirement() {
    return new SecurityRequirement().addList(JWT_AUTH);
  }

  private Components components() {
    return new Components().addSecuritySchemes(JWT_AUTH, securityScheme());
  }

  private SecurityScheme securityScheme() {
    return new SecurityScheme().name(JWT_AUTH)
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT");
  }

}
