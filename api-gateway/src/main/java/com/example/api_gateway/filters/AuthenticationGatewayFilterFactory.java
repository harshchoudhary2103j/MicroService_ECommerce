package com.example.api_gateway.filters;

import com.example.api_gateway.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private final JwtService jwtService;
    public AuthenticationGatewayFilterFactory(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if(!config.isEnabled){
                return chain.filter(exchange);
            }

           String header = exchange.getRequest().getHeaders().getFirst("Authorization");
           if(header == null || !header.startsWith("Bearer ")){
               exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
               return exchange.getResponse().setComplete();
           }
           String token = header.split("Bearer ")[1];
           Long userId = jwtService.getUserIdFromToken(token);
           exchange
                   .getRequest()
                   .mutate()
                   .header("X-User-Id", userId.toString())
                   .build();
           return chain.filter(exchange);
        };
    }

    public static class Config{
        private boolean isEnabled;
    }
}
