package com.blog.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ArticleTest {

    @Test
    public void testArticleGettersSetters() {
        LocalDateTime now = LocalDateTime.now();
        Article article = new Article();
        article.setId(1L);
        article.setTitle("Test Title");
        article.setContent("Test Content");
        article.setPublishDate(now);
        article.setLastUpdated(now);

        assertEquals(1L, article.getId());
        assertEquals("Test Title", article.getTitle());
        assertEquals("Test Content", article.getContent());
        assertEquals(now, article.getPublishDate());
        assertEquals(now, article.getLastUpdated());
    }

    @Test
    public void testArticleAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Article article = new Article(1L, "Title", "Content", now, now);

        assertEquals(1L, article.getId());
        assertEquals("Title", article.getTitle());
        assertEquals("Content", article.getContent());
        assertEquals(now, article.getPublishDate());
        assertEquals(now, article.getLastUpdated());
    }
} 