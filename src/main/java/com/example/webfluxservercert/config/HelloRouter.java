package com.example.webfluxservercert.config;

import com.example.webfluxservercert.filter.ExampleHandlerFilterFunction;
import com.example.webfluxservercert.handler.HelloHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class HelloRouter {

    @Value("${server.ssl.allowed-commonnames:}")
    private List<String> allowedCommonnames;

    @Bean
    public RouterFunction <ServerResponse> route(final HelloHandler helloHandler) {
        System.out.println("valuesArray ; " + allowedCommonnames);
        return RouterFunctions
                .route(GET("/ws").and(accept(APPLICATION_JSON)), helloHandler::hello)
                .filter(new ExampleHandlerFilterFunction(allowedCommonnames));
    }
}