package com.blog.controller;

import com.blog.entity.Article;
import com.blog.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BlogController.class)
public class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @Test
    @DisplayName("GET /blog returns blog/index view")
    public void testBlogHome() throws Exception {
        Article article = new Article(1L, "Test Title", "Test Content", LocalDateTime.now(), LocalDateTime.now());
        when(articleService.getAllArticles()).thenReturn(Arrays.asList(article));

        mockMvc.perform(get("/blog"))
                .andExpect(status().isOk())
                .andExpect(view().name("blog/index"));
    }

    @Test
    @DisplayName("GET /blog/create returns blog/edit view")
    public void testCreateArticleForm() throws Exception {
        mockMvc.perform(get("/blog/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("blog/edit"));
    }

    @Test
    @DisplayName("GET /blog/{id} returns blog/view view")
    public void testViewArticle() throws Exception {
        Article article = new Article(1L, "Test Title", "Test Content", LocalDateTime.now(), LocalDateTime.now());
        when(articleService.getArticleById(anyLong())).thenReturn(article);

        mockMvc.perform(get("/blog/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("blog/view"));
    }
} 