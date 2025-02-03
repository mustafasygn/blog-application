package com.blog.controller;

import com.blog.entity.Article;
import com.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final ArticleService articleService;

    @Autowired
    public BlogController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // HTML endpoints
    @Operation(summary = "Get blog homepage", description = "Displays all articles with optional date filtering")
    @GetMapping
    public String blogHome(@RequestParam(name = "publishedDate", required = false) String publishedDate,
                           Model model) {
        if (publishedDate != null && !publishedDate.isEmpty()) {
            java.time.LocalDateTime filterDate = java.time.LocalDate.parse(publishedDate).atStartOfDay();
            model.addAttribute("articles", articleService.getArticlesPublishedBefore(filterDate));
            model.addAttribute("publishedDate", publishedDate);
        } else {
            model.addAttribute("articles", articleService.getAllArticles());
        }
        return "blog/index";
    }

    @Operation(summary = "Show creation form", description = "Displays empty article creation form")
    @GetMapping("/create")
    public String createArticleForm(Model model) {
        model.addAttribute("article", new Article());
        return "blog/edit";
    }

    @Operation(summary = "Show edit form", description = "Displays article edit form for specified ID")
    @GetMapping("/edit/{id}")
    public String editArticleForm(@PathVariable Long id, Model model) {
        model.addAttribute("article", articleService.getArticleById(id));
        return "blog/edit";
    }

    // REST API endpoints
    @Operation(summary = "Create article", description = "Handles form submission for new articles")
    @PostMapping("/create")
    public String createArticle(@ModelAttribute Article article) {
        articleService.saveArticle(article);
        return "redirect:/blog";
    }

    @Operation(summary = "Update article", description = "Handles form submission for updating existing articles")
    @PostMapping("/edit/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute Article article) {
        articleService.updateArticle(id, article);
        return "redirect:/blog";
    }

    @Operation(summary = "Delete article", description = "Handles article deletion requests")
    @PostMapping("/{id}/delete")
    public String deleteArticlePost(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return "redirect:/blog";
    }

    @Operation(summary = "View article", description = "Displays full details of a specific article")
    @GetMapping("/{id}")
    public String viewArticle(
        @Parameter(description = "ID of article to view") @PathVariable Long id, 
        Model model) {
        model.addAttribute("article", articleService.getArticleById(id));
        return "blog/view";
    }
} 