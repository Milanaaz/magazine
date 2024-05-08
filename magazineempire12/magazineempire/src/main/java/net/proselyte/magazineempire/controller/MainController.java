package net.proselyte.magazineempire.controller;

import lombok.RequiredArgsConstructor;
import net.proselyte.magazineempire.model.Article;
import net.proselyte.magazineempire.model.Category;
import net.proselyte.magazineempire.model.News;
import net.proselyte.magazineempire.model.Podcast;
import net.proselyte.magazineempire.repository.CategoryRepository;
import net.proselyte.magazineempire.service.ArticleService;
import net.proselyte.magazineempire.service.PodcastService;
import net.proselyte.magazineempire.service.CategoryService;
import net.proselyte.magazineempire.service.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final CategoryRepository categoryRepository;
    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final NewsService newsService;
    private final PodcastService podcastService;

    @GetMapping("/")
    public String home(Model model) {
        return "quiz";
    }

    @GetMapping("/compilations")
    public String compilations(Model model) {
        return "compilations";
    }
    @GetMapping("/store")
    public String store(Model model) {
        return "store";
    }

    @GetMapping("/news")
    public String findAll(Model model) {
        List<News> news = newsService.findAll();
        model.addAttribute("news", news);
        return "news";
    }

    @GetMapping("/news/{id}")
    public String newsDetails(@PathVariable Long id, Model model) {
        News news = newsService.findById(id);
        model.addAttribute("id", id);
        model.addAttribute("news", news);
        model.addAttribute("images", news.getImages());
        return "section-news";
    }

    @GetMapping("/podcasts")
    public String podcasts(Model model) {
        List<Podcast> podcasts = podcastService.findAll();
        model.addAttribute("podcasts", podcasts);
        return "podcasts";
    }
    @GetMapping("/podcasts/{id}")
    public String podcastsDetails(@PathVariable Long id, Model model) {
        Podcast podcast = podcastService.findById(id);
        model.addAttribute("id", id);
        model.addAttribute("podcast", podcast);
        model.addAttribute("images", podcast.getImages());
        return "section-podcast";
    }


    @GetMapping("section/{categoryId}")
    public String sectionsCategory(@PathVariable("categoryId") Long categoryId, Model model) {
        Category category = categoryService.findById(categoryId);
        List<Article> articles = articleService.getArticlesByCategoryId(categoryId);
        model.addAttribute("category", category);
        model.addAttribute("articles", articles);
        return "section";
    }
    @GetMapping("/section/article/{id}")
    public String sectionArticle(@PathVariable Long id, Model model) {
        Article article = articleService.findById(id);
        model.addAttribute("id", id);
        model.addAttribute("article", article);
        model.addAttribute("images", article.getImages());
        return "section-article";
    }

}


