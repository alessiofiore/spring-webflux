package com.example.webflux.config;

import com.example.webflux.dto.WebFluxMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.FluxSink;

@Configuration
public class WebFluxConfig {

    @Bean
    public FluxProcessor processor() {
        return DirectProcessor.create().serialize();
    }

    @Bean
    public FluxSink<WebFluxMessage> fluxSink() {
        return processor().sink();
    }
}
