package com.example.webflux.service;

import com.example.webflux.dto.WebFluxMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.*;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class WebFluxService {

//    @Autowired
//    private FluxProcessor processor;
//
//    @Autowired
//    private FluxSink<WebFluxMessage> fluxSink;

    @Autowired
    private Sinks.Many<WebFluxMessage> fluxSink;

    private AtomicLong counter;

    @PostConstruct
    public void init() {
        this.counter = new AtomicLong();
    }

    /**
     * Create a message and add to FluxSink
     * @param destId
     * @return
     */
    public Long sendToDest(Integer destId) {
        WebFluxMessage m = new WebFluxMessage();
        long id = counter.getAndIncrement();
        m.setId(id);
        m.setDestId(destId);
        m.setValue("Hello " + destId);
//        fluxSink.next(m);
        fluxSink.emitNext(m, new ErroreSink());
        return id;
    }

    public Flux<WebFluxMessage> subscribe(Integer destId) {
//        return processor.filter(e -> ((WebFluxMessage) e).getDestId().equals(destId));
        return fluxSink.asFlux().filter(e -> ((WebFluxMessage) e).getDestId().equals(destId));
    }

    public class ErroreSink implements Sinks.EmitFailureHandler{

        @Override
        public boolean onEmitFailure(SignalType signalType, Sinks.EmitResult emitResult) {
            System.out.println(emitResult.name());
            return false;
        }
    }
}
