package com.springai.spring_ai_samples.records;

import jakarta.annotation.Nullable;

import java.util.UUID;

public record ChatRequest(@Nullable UUID chatId, String question) {
}