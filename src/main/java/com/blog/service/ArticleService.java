package com.blog.service;

import com.blog.entity.Article;
import com.blog.exception.ArticleNotFoundException;
import com.blog.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAllOrderByPublishDateDesc();
    }

    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found with id: " + id));
    }

    public Article saveArticle(Article article) {
        article.setPublishDate(LocalDateTime.now());
        article.setLastUpdated(LocalDateTime.now());
        return articleRepository.save(article);
    }

    public Article updateArticle(Long id, Article articleDetails) {
        Article article = getArticleById(id);
        article.setTitle(articleDetails.getTitle());
        article.setContent(articleDetails.getContent());
        article.setLastUpdated(LocalDateTime.now());
        return articleRepository.save(article);
    }

    public void deleteArticle(Long id) {
        Article article = getArticleById(id);
        articleRepository.delete(article);
    }

    public List<Article> getArticlesPublishedBefore(LocalDateTime dateTime) {
        return articleRepository.findByPublishDateBeforeOrderByPublishDateDesc(dateTime);
    }
} 