package com.example.webflux.controller;

import com.example.webflux.service.WebFluxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;

@RestController
public class WebFluxController {

    @Autowired
    private WebFluxService webFluxService;

    @GetMapping("/send/{dest}")
    public Long sendToDest(@PathVariable("dest") Integer dest) {
       return webFluxService.sendToDest(dest);
    }

    @GetMapping(value = "/subscribe-sse/{destId}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ServerSentEvent> subscribe(@PathVariable("destId") Integer destId) {
        return webFluxService.subscribe(destId)
                .map(e -> ServerSentEvent.builder(e).build());
    }

    /**
     * -----------------------------------------------
     * Other examples
     * -----------------------------------------------
     */

    @GetMapping(value = "/stream", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<String> streamEvents() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> "SSE - " + LocalTime.now().toString());
    }

    @GetMapping(value = "/intStream", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<String> intStream() {
        Flux<String> source = Flux.just(10, 20, 30, 40)
                .map(e -> e * 2)
                .log("Int before:")
                .map(i -> i + ";");

        source.subscribe(System.out::println);

        return source;
    }
}
