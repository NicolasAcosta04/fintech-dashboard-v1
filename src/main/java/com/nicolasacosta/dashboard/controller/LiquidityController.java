package com.nicolasacosta.dashboard.controller;

import com.nicolasacosta.dashboard.service.LiquidityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/liquidity")
public class LiquidityController {
    private final LiquidityService liquidityService;

    @GetMapping(value = "/global-m2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> getGlobalM2() {
        return Flux.interval(java.time.Duration.ofSeconds(10))
                .map(tick -> {
                    try {
                        return liquidityService.getLatestGlobalM2();
                    } catch (Exception e) {
                        log.error("Error fetching Global M2 data", e);
                        return -1; // or some error value
                    }
                })
                .doOnSubscribe(subscription -> log.info("Client has subscribed to Global M2 data stream"))
                .doOnCancel(() -> log.info("Client has cancelled Global M2 data stream"))
                .doOnError(error -> log.error("Error in Global M2 data stream", error));
    }
}
