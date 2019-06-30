package com.example.webfluxservercert.filter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ExampleWebFilter implements WebFilter {

    @Override
    public Mono <Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        serverWebExchange.getResponse().getHeaders().add("cn",extractCommonName(serverWebExchange));
        return webFilterChain.filter(serverWebExchange);
    }

    private String  extractCommonName(ServerWebExchange serverWebExchange){
        final String[] commonName = {""};
        Arrays.stream(serverWebExchange.getRequest().getSslInfo().getPeerCertificates()).anyMatch(s -> {
            Pattern subjectDnPattern = Pattern.compile("CN=(.*?)(?:,|$)");
            Matcher matcher = subjectDnPattern.matcher(s.getSubjectDN().getName());
            if (matcher.find()){
               commonName[0] = matcher.group(1);
               return true;
            }
            return false;
        });
        return commonName[0];
    }

}