package org.algiri.crud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${spring.application.version}")
    private String version;

    @Bean
    public Docket pipeApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .version(version)
                        .title(appName)
                        .build())
                .select()
                .apis(withClassAnnotation(RestController.class)
                        .or(input -> input.getPatternsCondition().getPatterns().stream().anyMatch(regex("/refresh"))))
                .paths(regex("/refresh.json").negate())
                .build()
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.POST,
                        List.of(new ResponseBuilder()
                                .code("500")
                                .description("500 message").build()));
    }
}