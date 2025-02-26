package com.springai.spring_ai_samples.service;

import com.springai.spring_ai_samples.records.ChatRequest;
import com.springai.spring_ai_samples.records.ChatResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatInMemoryService {

    private final ChatClient chatClient;

    public ChatInMemoryService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public ChatResponse chat(ChatRequest chatRequest) {
        UUID chatId = Optional
                .ofNullable(chatRequest.chatId())
                .orElse(UUID.randomUUID());
        String answer = chatClient
                .prompt()
                .user(chatRequest.question())
                .advisors(advisorSpec ->
                        advisorSpec
                                .param("chat_memory_conversation_id", chatId))
                .call()
                .content();
        return new ChatResponse(chatId, answer);
    }
}
