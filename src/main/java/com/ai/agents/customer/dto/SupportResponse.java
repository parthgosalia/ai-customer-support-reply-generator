package com.ai.agents.customer.dto;

import java.util.List;

public class SupportResponse {

    private String response;
    private double confidenceScore;
    private List<String> riskFlags;
    private String toneUsed;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public double getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public List<String> getRiskFlags() {
        return riskFlags;
    }

    public void setRiskFlags(List<String> riskFlags) {
        this.riskFlags = riskFlags;
    }

    public String getToneUsed() {
        return toneUsed;
    }

    public void setToneUsed(String toneUsed) {
        this.toneUsed = toneUsed;
    }
}
