package com.ai.agents.customer.client;

import java.util.List;

public class GeminiMapper {

    public static GeminiRequest buildRequest(String prompt) {

        GeminiRequest.Part part = new GeminiRequest.Part();
        part.setText(prompt);

        GeminiRequest.Content content = new GeminiRequest.Content();
        content.setParts(List.of(part));

        GeminiRequest request = new GeminiRequest();
        request.setContents(List.of(content));

        return request;
    }
}
