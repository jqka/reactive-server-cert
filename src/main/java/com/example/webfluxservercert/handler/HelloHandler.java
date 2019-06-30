package com.example.webfluxservercert.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class HelloHandler {
    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok().body(fromObject("Hello World"));
    }

}
