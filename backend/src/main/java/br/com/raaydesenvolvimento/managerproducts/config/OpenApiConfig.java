package br.com.raaydesenvolvimento.managerproducts.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Manager Products API")
                        .version("1.0")
                        .description("A Manager Products API é uma solução RESTful desenvolvida para gerenciar produtos e categorias de uma aplicação. Esta API oferece funcionalidades completas de CRUD (Create, Read, Update, Delete) para produtos e categorias, com autenticação baseada em JWT (JSON Web Tokens). Além disso, a API permite que os usuários registrem e autentiquem suas credenciais para acesso seguro aos recursos. A documentação abaixo fornece detalhes sobre os endpoints disponíveis, os parâmetros necessários, e as respostas esperadas para cada operação. Esta API foi construída utilizando Spring Boot, Spring Security, e Spring Data JPA, garantindo uma arquitetura robusta e escalável."))
                .addSecurityItem(new SecurityRequirement().addList("JWT Token"))
                .components(new Components()
                        .addSecuritySchemes("JWT Token",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
}