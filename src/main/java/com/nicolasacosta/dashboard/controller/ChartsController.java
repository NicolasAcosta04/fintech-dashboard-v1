package com.nicolasacosta.dashboard.controller;

import com.nicolasacosta.dashboard.data.FTSE100Data;
import com.nicolasacosta.dashboard.data.SPXData;
import com.nicolasacosta.dashboard.service.FTSE100Service;
import com.nicolasacosta.dashboard.service.SPXService;
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
@RequestMapping("/charts")
public class ChartsController {

    private final FTSE100Service ftse100Service;
    private final SPXService spxService;

    @GetMapping(value = "/UKX", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<FTSE100Data> streamFTSE100Prices() {
        log.info("Starting FTSE100 price stream");

        return Flux.interval(Duration.ofSeconds(1))
                .map(tick -> ftse100Service.generateTick())
                .doOnSubscribe(subscription -> log.info("Client subscribed to FTSE100 stream"))
                .doOnCancel(() -> log.info("Client cancelled FTSE100 stream"))
                .doOnError(error -> log.error("Error in FTSE100 stream", error));
    }

    @GetMapping(value = "/SPX", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<SPXData> streamSPXPrices() {
        log.info("Starting SPX price stream");

        return Flux.interval(Duration.ofSeconds(1))
                .map(tick -> spxService.generateTick())
                .doOnSubscribe(subscription -> log.info("Client subscribed to S&P 500 stream"))
                .doOnCancel(() -> log.info("Client cancelled SPX stream"))
                .doOnError(error -> log.error("Error in SPX stream", error));
    }
}
