package com.springai.spring_llm_rag.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    private final ChatClient chatClientQuestionAdvisor;
    private final ChatClient chatClientMessageChatMemoryAdvisor;

    public ChatController(ChatClient.Builder builder, VectorStore vectorStore, ChatClient chatClientMessageChatMemoryAdvisor) {

        this.chatClientMessageChatMemoryAdvisor = builder
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
        this.chatClientQuestionAdvisor = builder
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
                .build();
    }

    @GetMapping("/advisor")
    public String chatAdvisor(@RequestParam(defaultValue = "How did the Federal Reserve's recent interest " +
            "rate cut impact various asset classes according to the analysis") String query) {
        return chatClientQuestionAdvisor.prompt()
                .user(query)
                .call()
                .content();
    }

    @GetMapping("/chat-memory")
    public String chatMemory(@RequestParam String message) {
        return chatClientMessageChatMemoryAdvisor.prompt()
                .user(message)
                .call()
                .content();
    }
}
