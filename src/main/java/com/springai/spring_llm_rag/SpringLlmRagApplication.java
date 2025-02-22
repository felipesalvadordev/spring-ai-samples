package com.springai.spring_llm_rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringLlmRagApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringLlmRagApplication.class, args);
	}

	@Bean
	public ChatClient openAIChatClientCreate(OpenAiChatModel chatModel) {
		return ChatClient.create(chatModel);
	}

	@Bean
	public ChatClient.Builder openAIChatClientBuilder(OpenAiChatModel chatModel) {
		return ChatClient.builder(chatModel);
	}
}
