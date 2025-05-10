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
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.model.Media;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    private final ChatClient chatClientQuestionAdvisor;
    private final ChatClient chatClientMessageChatMemory;
    private final ChatClient chatClientBookRecomendation;
    private final OpenAiChatModel openAiChatClient;

    private final ChatInMemoryService chatInMemoryService;

    public ChatController(ChatClient.Builder builder, VectorStore vectorStore, OpenAiChatModel openAiChatClient, ChatInMemoryService chatInMemoryService) {

        this.chatClientMessageChatMemory = builder
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();

        this.openAiChatClient = openAiChatClient;

        this.chatInMemoryService = chatInMemoryService;

        this.chatClientQuestionAdvisor = builder
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
                .build();

        this.chatClientBookRecomendation = builder.build();
    }

    @GetMapping("/advisor-using-vector-store")
    public String chatAdvisor(@RequestParam(defaultValue = "How did the Federal Reserve's recent interest " +
            "rate cut impact various asset classes according to the analysis") String query) {
        return chatClientQuestionAdvisor.prompt()
                .user(query)
                .call()
                .content();
    }

    @GetMapping("/chat-with-memory")
    public String chatMemory(@RequestParam String message) {
        return chatClientMessageChatMemory.prompt()
                .user(message)
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

    @GetMapping("/reviews")
    public String bookReview(@RequestParam(value = "book", defaultValue = "The Shinning") String book) {
        PromptTemplate promptTemplate = new PromptTemplate("""
                Please provide me with
                a brief review of the book {book}
                and also the biography of its author.""");
        promptTemplate.add("book", book);
        return openAiChatClient.call(promptTemplate.create()).getResult().getOutput().getText();
    }

    @GetMapping("/image-explained")
    public String imageExplained() {
        return ChatClient.create(openAiChatClient)
                .prompt()
                .user(u -> u.text("Explain what do you see on this picture?")
                        .media(Media.Format.IMAGE_JPEG, new ClassPathResource("/images/my-dog.jpg")))
                .call()
                .content();
    }
}
