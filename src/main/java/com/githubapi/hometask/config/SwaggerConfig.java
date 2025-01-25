package com.githubapi.hometask.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

  @Bean
  public OpenAPI appApiDefinition() {
    Info info = new Info();
    info.setTitle("Secfix github-Integration APIs");
    info.setDescription("Just A Home task version");
    info.version("V1.0.0");
    return new OpenAPI()
        .info(new Info()
            .title("Observed Repos API")
            .version("v1")
            .description("API for managing and monitoring observed GitHub repositories")
        );
  }
}
