package com.nicolasacosta.dashboard.controller;

import com.nicolasacosta.dashboard.data.FearAndGreedData;
import com.nicolasacosta.dashboard.service.SentimentService;
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
@AllArgsConstructor
@RequestMapping("/sentiment")
public class SentimentController {

    private final SentimentService sentimentService;

    @GetMapping(value = "/cnn-fear-and-greed", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<FearAndGreedData> streamCnnFearAndGreedData() {
        log.info("Starting CNN Fear and Greed Index data stream");

        return Flux.interval(Duration.ofSeconds(60))
                .map(tick -> sentimentService.getCnnFearAndGreedData())
                .doOnSubscribe(subscription -> log.info("Client has subscribed to CNN Fear and Greed Index stream"))
                .doOnCancel(() -> log.info("Client has cancelled CNN Fear and Greed Index stream"))
                .doOnError(error -> log.error("Error in CNN Fear and Greed Index stream", error));
    }

    @GetMapping(value = "/crypto-fear-and-greed", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<FearAndGreedData> streamCryptoFearAndGreedData() {
        log.info("Starting Crypto Fear and Greed Index data stream");

        return Flux.interval(Duration.ofSeconds(60))
                .map(tick -> sentimentService.getCryptoFearAndGreedData())
                .doOnSubscribe(subscription -> log.info("Client has subscribed to Crypto Fear and Greed Index stream"))
                .doOnCancel(() -> log.info("Client has cancelled Crypto Fear and Greed Index stream"))
                .doOnError(error -> log.error("Error in Crypto Fear and Greed Index stream", error));
    }
}
