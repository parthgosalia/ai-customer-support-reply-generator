package com.ai.agents.customer.util;

import java.util.ArrayList;
import java.util.List;

public class RiskDetector {

    public static List<String> detect(String complaint) {

        List<String> risks = new ArrayList<>();
        String text = complaint.toLowerCase();

        if (text.contains("refund") || text.contains("money") || text.contains("payment")) {
            risks.add("financial_issue");
        }

        if (text.contains("legal") || text.contains("lawyer")) {
            risks.add("legal_risk");
        }

        return risks;
    }
}
