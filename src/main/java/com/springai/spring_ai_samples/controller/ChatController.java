package com.springai.spring_ai_samples.controller;

import com.springai.spring_ai_samples.domain.BookRecommendation;
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
    private final ChatClient chatClientBookRecomendation;

    public ChatController(ChatClient.Builder builder, VectorStore vectorStore) {

        this.chatClientMessageChatMemoryAdvisor = builder
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();

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

    @GetMapping("/chat-memory")
    public String chatMemory(@RequestParam String message) {
        return chatClientMessageChatMemoryAdvisor.prompt()
                .user(message)
                .call()
                .content();
    }

    @GetMapping("/book-recomendations")
    public BookRecommendation chatWithEntityReturn() {
        return chatClientBookRecomendation.prompt()
                .user("Generate a book recommendation for a book on AI and coding. Please limit the summary to 100 words.")
                .call()
                .entity(BookRecommendation.class);
    }
}
