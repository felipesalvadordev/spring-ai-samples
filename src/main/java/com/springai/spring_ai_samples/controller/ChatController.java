package com.springai.spring_ai_samples.controller;

import com.springai.spring_ai_samples.records.BookRecommendation;
import com.springai.spring_ai_samples.records.ChatRequest;
import com.springai.spring_ai_samples.records.ChatResponse;
import com.springai.spring_ai_samples.service.ChatInMemoryService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    private final ChatClient chatClientQuestionAdvisor;
    private final ChatClient chatClientMessageChatMemoryAdvisor;
    private final ChatClient chatClientBookRecomendation;

    private final ChatInMemoryService chatInMemoryService;

    public ChatController(ChatClient.Builder builder, VectorStore vectorStore, ChatInMemoryService chatInMemoryService) {

        this.chatClientMessageChatMemoryAdvisor = builder
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
        this.chatInMemoryService = chatInMemoryService;

        this.chatClientQuestionAdvisor = builder
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
                .build();

        this.chatClientBookRecomendation = builder.build();;
    }

    @GetMapping("/advisor")
    public String chatAdvisor(@RequestParam(defaultValue = "How did the Federal Reserve's recent interest " +
            "rate cut impact various asset classes according to the analysis") String query) {
        return chatClientQuestionAdvisor.prompt()
                .user(query)
                .call()
                .content();
    }

    @PostMapping("/chatbot")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest chatRequest) {
        ChatResponse chatResponse = chatInMemoryService.chat(chatRequest);
        return ResponseEntity.ok(chatResponse);
    }

    @GetMapping("/book-recomendations")
    public BookRecommendation chatWithEntityReturn() {
        return chatClientBookRecomendation.prompt()
                .user("Generate a book recommendation for a book on AI and coding. Please limit the summary to 100 words.")
                .call()
                .entity(BookRecommendation.class);
    }
}
