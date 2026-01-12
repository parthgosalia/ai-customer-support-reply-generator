package com.ai.agents.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ai.agents.customer.client.AiClient;
import com.ai.agents.customer.config.ToneDetector;
import com.ai.agents.customer.dto.SupportRequest;
import com.ai.agents.customer.dto.SupportResponse;
import com.ai.agents.customer.util.RiskDetector;
import com.ai.agents.customer.util.TokenUtil;

import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SupportService {

    private static final Logger log = LoggerFactory.getLogger(SupportService.class);

    private final AiClient aiClient;

    public SupportService(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    public Mono<SupportResponse> generateReply(SupportRequest request) {

        String prompt = buildPrompt(request);
    
        int inputTokens = TokenUtil.estimateTokens(prompt);
        log.info("Estimated input tokens: {}", inputTokens);
    
        return aiClient.callLLM(prompt)
                .map(aiReply -> {
    
                    int outputTokens = TokenUtil.estimateTokens(aiReply);
                    log.info("Estimated output tokens: {}", outputTokens);
    
                    List<String> risks = RiskDetector.detect(request.getComplaint());
    
                    SupportResponse response = new SupportResponse();
                    response.setResponse(aiReply);
                    response.setConfidenceScore(0.92);
                    response.setRiskFlags(risks);
                    response.setToneUsed("empathetic_professional");
    
                    return response;
                }).onErrorResume(ex -> {
                    log.error("Gemini call failed", ex);
        
                    SupportResponse fallback = new SupportResponse();
                    fallback.setResponse(
                            "We apologize for the inconvenience. Our support team will contact you shortly."
                    );
                    fallback.setConfidenceScore(0.0);
                    fallback.setRiskFlags(List.of("ai_unavailable"));
                    fallback.setToneUsed("fallback_professional");
        
                    return Mono.just(fallback);
                });
    }
    

    private String buildPrompt(SupportRequest request) {
        String tone = ToneDetector.detect(request.getComplaint());
        System.err.println("Tone used %s".formatted(tone) );
        return """
        You are a professional customer support agent.

        Product: %s
        Customer tone: %s

        Complaint:
        %s

        Write a polite, empathetic, professional response.
        """
        .formatted(
                request.getProduct(),
                tone,
                request.getComplaint()
        );
    }
}
