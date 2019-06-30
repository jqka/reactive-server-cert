package com.example.webfluxservercert.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import static org.springframework.http.HttpStatus.*;

@Component
public class ExampleHandlerFilterFunction implements HandlerFilterFunction <ServerResponse, ServerResponse> {

    private List<String> allowedCommonnames;

    @Autowired
    public ExampleHandlerFilterFunction(final List<String> allowedCommonnames){
        this.allowedCommonnames = allowedCommonnames;
    }

    @Override
    public Mono <ServerResponse> filter(ServerRequest serverRequest, HandlerFunction <ServerResponse> handlerFunction) {
        System.out.println("Cn : " + serverRequest.exchange().getResponse().getHeaders().get("cn"));

        boolean aa = serverRequest.exchange().getResponse().getHeaders().get("cn").stream().anyMatch(allowedCommonnames::contains);

        if (aa){
            return handlerFunction.handle(serverRequest);
        }
        return ServerResponse.status(UNAUTHORIZED).build();
    }
}