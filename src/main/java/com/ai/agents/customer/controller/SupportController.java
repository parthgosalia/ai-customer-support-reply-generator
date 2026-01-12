package com.ai.agents.customer.controller;



import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.*;

import com.ai.agents.customer.dto.SupportRequest;
import com.ai.agents.customer.dto.SupportResponse;
import com.ai.agents.customer.service.SupportService;

@RestController
@RequestMapping("/api/support")
public class SupportController {

    private final SupportService supportService;

    public SupportController(SupportService supportService) {
        this.supportService = supportService;
    }

    @PostMapping("/generate-reply")
    public Mono<SupportResponse> generateReply(
            @Valid @RequestBody SupportRequest request
    ) {
        return supportService.generateReply(request);
    }
    
}