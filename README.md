# Spring AI Samples 

A demonstration project showcasing Retrieval Augmented Generation (RAG) implementation using Spring AI and OpenAI's GPT models. This application enables intelligent document querying by combining the power of Large Language Models (LLMs) with local document context.  
And a demonstration of chat memory using MessageChatMemoryAdvisor.
## Overview

This project demonstrates how to:
- Ingest PDF documents into a vector database
- Perform semantic searches using Spring AI
- Augment LLM responses with relevant document context
- Create an API endpoint for document-aware chat interactions
- Build a ChatClient with a MessageChatMemoryAdvisor that uses InMemoryChatMemory. This setup allows our chatbot to remember previous interactions.

Query the API using curl or your preferred HTTP client:

```bash
curl http://localhost:8080/advisor
```
The response will include context from your documents along with the LLM's analysis.

```bash
curl http://localhost:8080/chat-memory
```
The response will be a chatbot with memory capabilities.

## Architecture Anatomy of RAG and LLM process

- **Document Processing**: Uses Spring AI's PDF document reader to parse documents into manageable chunks
- **Vector Storage**: Utilizes PGVector for efficient similarity searches
- **Context Retrieval**: Automatically retrieves relevant document segments based on user queries
- **Response Generation**: Combines document context with GPT-4's capabilities for informed responses

## References
https://www.danvega.dev/blog/getting-started-with-spring-ai-rag  
https://www.danvega.dev/blog/spring-ai-chat-memory
