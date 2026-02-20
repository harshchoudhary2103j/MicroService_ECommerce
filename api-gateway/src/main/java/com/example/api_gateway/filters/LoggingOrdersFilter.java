package com.example.api_gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingOrdersFilter extends AbstractGatewayFilterFactory {
    public LoggingOrdersFilter() {
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            log.info("Request received at order service: " + exchange.getRequest().getURI());
            return chain.filter(exchange).then(Mono.fromRunnable(
                    () -> log.info("Response sent from order service: " + exchange.getResponse().getStatusCode())
            ));
        };
    }
    public static class Config{}
}
