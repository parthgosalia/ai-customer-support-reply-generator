# 🧠 AI-Powered Customer Support Reply Generator

An AI-driven backend service that generates **professional, empathetic customer support replies** using **Google Gemini (gemini-2.5-flash)**.  
Built with **Spring Boot + Reactive WebClient**, this project demonstrates **real-world AI integration**, production-ready error handling, and clean backend architecture.

---

## 🚀 What This Project Does

This service accepts a customer complaint and product context, intelligently analyzes the message, and generates a high-quality support response using an LLM.

Unlike toy demos, this project:
- Uses **real AI APIs**
- Handles **timeouts, retries, and fallbacks**
- Detects **customer tone automatically**
- Logs **token usage**
- Is fully **non-blocking and reactive**

---

## 🧩 Key Features

### 🤖 AI-Powered Reply Generation
- Uses **Google Gemini (gemini-2.5-flash)** for fast and cost-effective responses
- Produces **empathetic, professional** customer replies

### 🧠 Intelligent Prompt Enrichment
- Automatically detects **customer tone** from complaint text
- Adds **product context** dynamically
- No reliance on user-provided sentiment labels

### 🔐 Resilient & Production-Ready
- Reactive, non-blocking API using **Spring WebFlux**
- Timeout handling for slow LLM responses
- Controlled retries for server-side failures (503 / UNAVAILABLE)
- Graceful fallback responses when AI is unavailable

### 📊 Token Awareness
- Estimates and logs:
  - Input tokens
  - Output tokens
- Useful for **cost tracking and optimization**

### ⚠️ Risk Detection
- Flags sensitive or risky complaints (e.g., payment, refund, legal)
- Helps downstream workflows (manual escalation, alerts, etc.)

---

## 🛠️ Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring WebFlux (Reactive)
- WebClient

### AI & NLP
- Google Gemini API
  - Model: `gemini-2.5-flash`
- Prompt engineering
- Rule-based tone detection

### Utilities
- Token estimation utility
- Risk detection utility
- Tone detection utility

### Build & Tools
- Maven
- Lombok
- SLF4J Logging

---

## 📂 Project Structure

```text
com.ai.agents.customer
 ├── controller        # REST API endpoints
 ├── service           # Business logic
 ├── client            # Gemini AI client
 ├── mapper            # AI request/response mapping
 ├── dto               # Request/response DTOs
 ├── util              # Token, tone, risk detection utilities
 └── config            # WebClient configuration
```

## 📡 API Endpoint
### Generate Support Reply

POST /api/support/generate-reply

### Request Body
```text
{
  "complaint": "My payment failed but money got deducted",
  "product": "Gym Membership App"
}
```
Customer tone is auto-detected from the complaint text.
Clients are not required to send tone explicitly.

### Response
```text
{
  "response": "We’re sorry to hear about the issue with your payment...",
  "confidenceScore": 0.92,
  "riskFlags": ["payment_issue"],
  "toneUsed": "empathetic_professional"
}
```
## ⚙️ Configuration
### application.properties
```text
# Gemini Configuration
gemini.api-key=YOUR_API_KEY
gemini.base-url=https://generativelanguage.googleapis.com/v1beta/models
gemini.model=gemini-2.5-flash
```

## 🧠 Design Principles Followed
- Separation of concerns
- Reactive-first design
- No blocking calls
- Fail-safe AI usage
- Configuration-driven model selection

## 💬 Interview Highlights
- This project demonstrates:
- Real LLM integration (not mocks)
- Reactive programming with WebFlux
- AI cost awareness (token logging)
- Intelligent prompt engineering
- Production-grade error handling
- Clean, scalable backend architecture

## 🧪 Possible Enhancements
- Model fallback (Flash → Pro)
- AI-based tone classification
- Rate limiting per user
- Request/response tracing
- Persistent analytics on complaints & tone

## 👤 Author
- Built as part of an AI Engineering journey to design practical, real-world AI-powered backend systems.

## ▶️ How to Run Locally

### Prerequisites
- Java 17+
- Maven 3.8+
- Google Gemini API Key

### Steps
```bash
git clone https://github.com/parthgosalia/ai-customer-support-reply-generator.git
cd ai-customer-support-reply-generator
mvn clean install
mvn spring-boot:run
```

The application will start at:
```bash
http://localhost:8080
```
## ⚠️ Error Handling & Fallback Strategy

- AI request timeouts are handled gracefully
- Retry mechanism is applied for transient server errors (503)
- If the AI service is unavailable, a predefined fallback response is returned
- No request causes the API to block or crash

This ensures high availability even when external AI services are unstable.

## 🧠 Tone Detection Logic

The system automatically determines the tone of the reply based on keywords
found in the customer complaint.

Examples:
- Refund, payment, legal → **Empathetic & Apologetic**
- Please, request, help → **Polite & Professional**
- Angry or aggressive words → **Calm & De-escalating**

When multiple tones are detected, priority is given to **risk-sensitive keywords**
(e.g., refund or payment issues override politeness).

## 🤔 Why Gemini 2.5 Flash?

- Lower latency compared to larger models
- Cost-effective for high-volume customer support use cases
- Sufficient reasoning for short, structured responses
- Ideal for real-time backend APIs

## 🔐 Security Considerations

- API keys are externalized using application configuration
- No sensitive data is logged
- Designed to be easily integrated with authentication and rate limiting layers
