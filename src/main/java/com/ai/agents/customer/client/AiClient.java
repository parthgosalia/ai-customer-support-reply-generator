package com.ai.agents.customer.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

@Component
public class AiClient {

    private final WebClient webClient;
    private final String apiKey;
    private final String model;

    public AiClient(
            WebClient.Builder webClientBuilder,
            @Value("${gemini.base-url}") String baseUrl,
            @Value("${gemini.api-key}") String apiKey,
            @Value("${gemini.model}") String model
    ) {
        this.apiKey = apiKey;
        this.model = model;

        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<String> callLLM(String prompt) {

        GeminiRequest request = GeminiMapper.buildRequest(prompt);

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/" + model + ":generateContent")
                        .queryParam("key", apiKey)
                        .build())
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(body ->
                                        Mono.error(new RuntimeException(
                                                "Gemini client error: " + body
                                        ))
                                )
                )
                .onStatus(
                        status -> status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(body ->
                                        Mono.error(new RuntimeException(
                                                "Gemini server error: " + body
                                        ))
                                )
                )
                .bodyToMono(GeminiResponse.class)
                .timeout(Duration.ofSeconds(30))
                .retryWhen(
                        Retry.backoff(1, Duration.ofSeconds(2))
                                .filter(ex ->
                                        ex instanceof TimeoutException
                                        || ex.getMessage().contains("UNAVAILABLE")
                                )
                )
                .map(response ->
                        response.getCandidates()
                                .get(0)
                                .getContent()
                                .getParts()
                                .get(0)
                                .getText()
                );
    }
}
