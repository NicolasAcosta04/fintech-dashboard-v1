package com.nicolasacosta.dashboard.controller;

import com.nicolasacosta.dashboard.data.GDPData;
import com.nicolasacosta.dashboard.service.EconService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
@RestController
@RequestMapping("/econ")
@AllArgsConstructor
public class EconController {

    private final EconService econService;

    @GetMapping(value = "/uk/gdp", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<GDPData> getUKGdp() {
        return Flux.interval(Duration.ofSeconds(10))
                .map(tick -> econService.getLatestUkGdp())
                .doOnSubscribe(subscription -> log.info("Client has subscribed to economic data stream"))
                .doOnCancel(() -> log.info("Client has cancelled economic data stream"))
                .doOnError(error -> log.error("Error in economic data stream", error));
    }

    @GetMapping(value = "/uk/interest-rate", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Double> getUKInterestRate() {
        return Flux.interval(Duration.ofSeconds(10))
                .map(tick -> econService.getLatestBOEInterestRate())
                .doOnSubscribe(subscription -> log.info("Client has subscribed to interest rate data stream"))
                .doOnCancel(() -> log.info("Client has cancelled interest rate data stream"))
                .doOnError(error -> log.error("Error in interest rate data stream", error));
    }

    @GetMapping(value = "/us/gdp", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<GDPData> getUSGdp() {
        return Flux.interval(Duration.ofSeconds(10))
                .map(tick -> econService.getLatestUSGdp())
                .doOnSubscribe(subscription -> log.info("Client has subscribed to US economic data stream"))
                .doOnCancel(() -> log.info("Client has cancelled US economic data stream"))
                .doOnError(error -> log.error("Error in US economic data stream", error));
    }

    @GetMapping(value = "/us/interest-rate", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Double> getUSInterestRate() {
        return Flux.interval(Duration.ofSeconds(10))
                .map(tick -> econService.getLatestFEDInterestRate())
                .doOnSubscribe(subscription -> log.info("Client has subscribed to US interest rate data stream"))
                .doOnCancel(() -> log.info("Client has cancelled US interest rate data stream"))
                .doOnError(error -> log.error("Error in US interest rate data stream", error));
    }
}
