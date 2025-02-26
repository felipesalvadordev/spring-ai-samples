package com.springai.spring_ai_samples.records;

import java.util.UUID;

public record ChatResponse(UUID chatId, String answer) {
}