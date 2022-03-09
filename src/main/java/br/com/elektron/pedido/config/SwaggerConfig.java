package br.com.elektron.pedido.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    private ApiInfo apiInfo() {
        return new ApiInfo("Sistema de Pedidos",
                "Implemetação de API para testes de conhecimentos",
                "1.0",
                null,
                new Contact("Edmilson Pontes", 
                		"https://www.linkedin.com/in/edmilsonpontes", 
                		"edmilsonpontes@gmail.com"
                	),
                null,
                null,
                Collections.emptyList());	
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.elektron.pedido.controller"))
                .build();
    }
    
    
}