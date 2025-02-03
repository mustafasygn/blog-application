package com.blog.service;

import com.blog.entity.Article;
import com.blog.exception.ArticleNotFoundException;
import com.blog.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleService articleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllArticles() {
        Article article1 = new Article(1L, "Title 1", "Content 1", LocalDateTime.now(), LocalDateTime.now());
        Article article2 = new Article(2L, "Title 2", "Content 2", LocalDateTime.now(), LocalDateTime.now());
        when(articleRepository.findAllOrderByPublishDateDesc()).thenReturn(Arrays.asList(article1, article2));

        List<Article> articles = articleService.getAllArticles();
        assertEquals(2, articles.size());
        verify(articleRepository).findAllOrderByPublishDateDesc();
    }

    @Test
    public void testGetArticleById_Found() {
        Article article = new Article(1L, "Title", "Content", LocalDateTime.now(), LocalDateTime.now());
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        Article found = articleService.getArticleById(1L);
        assertEquals("Title", found.getTitle());
    }

    @Test
    public void testGetArticleById_NotFound() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ArticleNotFoundException.class, () -> articleService.getArticleById(99L));
    }

    @Test
    public void testSaveArticle() {
        Article article = new Article(null, "New Title", "New Content", null, null);
        when(articleRepository.save(any(Article.class))).thenAnswer(invocation -> {
            Article saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        Article savedArticle = articleService.saveArticle(article);
        assertNotNull(savedArticle.getId());
        assertNotNull(savedArticle.getPublishDate());
        assertNotNull(savedArticle.getLastUpdated());
        verify(articleRepository).save(article);
    }

    @Test
    public void testUpdateArticle() {
        Article existing = new Article(1L, "Old Title", "Old Content", LocalDateTime.now(), LocalDateTime.now());
        when(articleRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(articleRepository.save(existing)).thenReturn(existing);

        Article updateDetails = new Article(null, "Updated Title", "Updated Content", null, null);
        Article updated = articleService.updateArticle(1L, updateDetails);

        assertEquals("Updated Title", updated.getTitle());
        assertEquals("Updated Content", updated.getContent());
        verify(articleRepository).save(existing);
    }

    @Test
    public void testDeleteArticle() {
        Article article = new Article(1L, "Title", "Content", LocalDateTime.now(), LocalDateTime.now());
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        articleService.deleteArticle(1L);
        verify(articleRepository).delete(article);
    }

    @Test
    public void testGetArticlesPublishedBefore() {
        LocalDateTime dateTime = LocalDateTime.now();
        Article article = new Article(1L, "Title", "Content", dateTime.minusDays(1), LocalDateTime.now());
        when(articleRepository.findByPublishDateBeforeOrderByPublishDateDesc(dateTime))
                .thenReturn(Arrays.asList(article));

        List<Article> articles = articleService.getArticlesPublishedBefore(dateTime);
        assertFalse(articles.isEmpty());
        verify(articleRepository).findByPublishDateBeforeOrderByPublishDateDesc(dateTime);
    }
} 