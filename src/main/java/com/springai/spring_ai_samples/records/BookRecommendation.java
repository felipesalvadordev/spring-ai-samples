package com.springai.spring_ai_samples.records;

public record BookRecommendation(
        String title,
        String author,
        int publicationYear,
        String genre,
        int pageCount,
        String summary
) {
}