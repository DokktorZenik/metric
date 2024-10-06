package com.example.metric.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class Config implements WebFilter {

    private static final Logger logger = LoggerFactory.getLogger(Config.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        logger.info("Incoming request to internal service: " + exchange.getRequest().getURI());
        return chain.filter(exchange);
    }
}
