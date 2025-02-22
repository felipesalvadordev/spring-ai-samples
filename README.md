# Spring AI Samples 

A demonstration project showcasing Retrieval Augmented Generation (RAG) implementation using Spring AI and OpenAI's GPT models. This application enables intelligent document querying by combining the power of Large Language Models (LLMs) with local document context.  
A demonstration of chat memory using MessageChatMemoryAdvisor.
And a demonstration of how to parse the AI's response into a structured Java object.
## Overview

This project demonstrates how to:
- Ingest PDF documents into a vector database
- Perform semantic searches using Spring AI
- Augment LLM responses with relevant document context
- Create an API endpoint for document-aware chat interactions
- Build a ChatClient with a MessageChatMemoryAdvisor that uses InMemoryChatMemory. This setup allows our chatbot to remember previous interactions.  
- Build a book recommendation system using Spring AI and Spring Boot and parse the AI's response into a structured Java object.

Query the API using curl or your preferred HTTP client:

```bash
curl http://localhost:8080/advisor
```
The response will include context from your documents along with the LLM's analysis.

```bash
curl http://localhost:8080/chat-memory?message="What's my name?"
```
The response will be a chatbot with memory capabilities.

```bash
curl http://localhost:8080/book-recomendations
```
This code demonstrates how to automatically parse the AI's response into a structured Java object whitout JSON mapping. 

## Architecture Anatomy of RAG and LLM process

- **Document Processing**: Uses Spring AI's PDF document reader to parse documents into manageable chunks
- **Vector Storage**: Utilizes PGVector for efficient similarity searches
- **Context Retrieval**: Automatically retrieves relevant document segments based on user queries
- **Response Generation**: Combines document context with GPT-4's capabilities for informed responses

## References
https://www.danvega.dev/blog/getting-started-with-spring-ai-rag  
https://www.danvega.dev/blog/spring-ai-chat-memory  
https://www.danvega.dev/blog/ai-java-developers
