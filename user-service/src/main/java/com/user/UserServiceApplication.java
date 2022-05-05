package com.user;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.reactive.function.client.WebClient;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableJpaRepositories(basePackages = { "com.data.*", "com.user.*" })
@ComponentScan(basePackages = { "com.data.*", "com.user.*" })
@EntityScan(basePackages = { "com.data.*", "com.user.*" })
@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@PropertySources(value = { @PropertySource("classpath:message.properties"),
        @PropertySource("classpath:exception.properties"), })
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    public Mapper mapper() {
        return new DozerBeanMapper();
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder getWebclientBuilder() {

        return WebClient.builder();
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).host("http://localhost:8081").select()
                .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();

    }

}
