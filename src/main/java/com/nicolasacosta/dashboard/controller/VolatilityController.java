package com.nicolasacosta.dashboard.controller;

import com.nicolasacosta.dashboard.data.VIXData;
import com.nicolasacosta.dashboard.service.VolatilityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/volatility")
public class VolatilityController {

    private final VolatilityService volatilityService;

    @GetMapping(value = "/VIX", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<VIXData> streamVIXData() {
        log.info("Starting VIX data stream");

        return Flux.interval(Duration.ofSeconds(1))
                .map(tick -> volatilityService.generateTick())
                .doOnSubscribe(subscription -> log.info("Client has subscribed to VIX stream"))
                .doOnCancel(() -> log.info("Client has cancelled VIX stream"))
                .doOnError(error -> log.error("Error in VIX stream", error));
    }
}
