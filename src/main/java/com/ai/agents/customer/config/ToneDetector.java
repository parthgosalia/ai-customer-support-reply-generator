package com.ai.agents.customer.config;

public class ToneDetector {

    public static String detect(String text) {

        String lower = text.toLowerCase();

        if (lower.contains("angry")
                || lower.contains("refund")
                || lower.contains("worst")
                || lower.contains("deducted")) {
            return "angry";
        }

        if (lower.contains("please")
                || lower.contains("kindly")) {
            return "polite";
        }

        return "neutral";
    }
}
