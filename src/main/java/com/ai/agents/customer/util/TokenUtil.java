package com.ai.agents.customer.util;

public class TokenUtil {

    public static int estimateTokens(String text) {
        return text.length() / 4;
    }
}

