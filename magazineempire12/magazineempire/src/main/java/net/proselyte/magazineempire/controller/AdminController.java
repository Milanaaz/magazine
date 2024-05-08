package net.proselyte.magazineempire.controller;

import lombok.RequiredArgsConstructor;
import net.proselyte.magazineempire.model.Article;
import net.proselyte.magazineempire.model.Category;
import net.proselyte.magazineempire.model.News;
import net.proselyte.magazineempire.model.Podcast;
import net.proselyte.magazineempire.repository.CategoryRepository;
import net.proselyte.magazineempire.repository.NewsRepository;
import net.proselyte.magazineempire.repository.PodcastRepository;
import net.proselyte.magazineempire.repository.ArticleRepository;
import net.proselyte.magazineempire.service.CategoryService;
import net.proselyte.magazineempire.service.ArticleService;
import net.proselyte.magazineempire.service.NewsService;
import net.proselyte.magazineempire.service.PodcastService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor

public class AdminController {
    private final CategoryService categoryService;
    private final ArticleService articleService;
    private final NewsService newsService;
    private final PodcastService podcastService;
    private final CategoryRepository categoryRepository;

    @GetMapping("/category")
    public String getAllCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("category", categories);
        return "category";
    }
    @GetMapping("/articles")
    public String findAllArticles(Model model) {
        List<Article> articles = articleService.findAll();
        model.addAttribute("articles", articles);
        return "article-list";
    }

    @GetMapping("article-delete/{id}")
    public String deleteArticle(@PathVariable("id") Long id) {
        articleService.deleteById(id);
        return "redirect:/articles";
    }
    @GetMapping("/category/{categoryId}")
    public String getArticlesByCategory(@PathVariable("categoryId") Long categoryId, Model model) {
        Category category = categoryService.findById(categoryId);
        List<Article> articles = articleService.getArticlesByCategoryId(categoryId);
        model.addAttribute("category", category);
        model.addAttribute("articles", articles);
        return "articles-by-category";
    }

    @GetMapping("/article-create")
    public String createArticleForm(Article article, Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "article-create";
    }

    @PostMapping("/article-create")
    public String createArticle(@RequestParam("files") MultipartFile[] files,
                                @RequestParam("categoryId") Long categoryId,
                                Article article) throws IOException {
        Category category = categoryService.findById(categoryId);
        article.setCategory(category);
        articleService.saveArticle(article, files);
        return "redirect:/articles";
    }

    @GetMapping("/article-update/{id}")
    public String updateArticleForm(@PathVariable("id") Long id, Model model) {
        Article article = articleService.findById(id);
        model.addAttribute("article", article);
        model.addAttribute("images", article.getImages());
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("selectedCategoryId", article.getCategory().getId());
        model.addAttribute("categories", categories);
        return "article-update";
    }

    @PostMapping("/article-update/{id}")
    public String updateArticle(@RequestParam("files") MultipartFile[] files,
                                @RequestParam("categoryId") Long categoryId,
                                Article article) throws IOException {
        Category category = categoryService.findById(categoryId);
        article.setCategory(category);
        articleService.deleteImages(article, files);
        articleService.saveArticle(article, files);
        return "redirect:/articles/" + article.getId();
    }

    @GetMapping("/articles/{id}")
    public String articleDetails(@PathVariable Long id, Model model) {
        Article article = articleService.findById(id);
        model.addAttribute("id", id);
        model.addAttribute("article", article);
        model.addAttribute("images", article.getImages());
        return "article-details";
    }



    @GetMapping("/admin/news")
    public String findAll(Model model) {
        List<News> news = newsService.findAll();
        model.addAttribute("news", news);
        return "news-list";
    }

    @GetMapping("/admin/news-create")
    public String createNewsForm(News news) {
        return "news-create";
    }

    @PostMapping("/admin/news-create")
    public String createNews(@RequestParam("files") MultipartFile[] files, News news) throws IOException {
        newsService.saveNews(news, files);
        return "redirect:/admin/news/" + news.getId();
    }

    @GetMapping("/admin/news-delete/{id}")
    public String deleteNews(@PathVariable("id") Long id) {
        newsService.deleteById(id);
        return "redirect:/admin/news";
    }

    @GetMapping("/admin/news-update/{id}")
    public String updateNewsForm(@PathVariable("id") Long id, Model model) {
        News news = newsService.findById(id);
        model.addAttribute("news", news);
        model.addAttribute("images", news.getImages());
        return "news-update";
    }
    @PostMapping("/admin/news-update")
    public String updateNews(@RequestParam("files") MultipartFile[] files, News news) throws IOException {
        newsService.deleteImages(news, files);
        newsService.saveNews(news, files);
        return "redirect:/admin/news/" + news.getId();
    }

    @GetMapping("/admin/news/{id}")
    public String newsDetails(@PathVariable Long id, Model model) {
        News news = newsService.findById(id);
        model.addAttribute("id", id);
        model.addAttribute("news", news);
        model.addAttribute("images", news.getImages());
        return "news-details";
    }

    @GetMapping("/section/ballet")
    public String sectionBallet(Model model) {
        return "section-ballet";
    }
    @GetMapping("/section/ballet/{id}")
    public String sectionArticle(@PathVariable Long id, Model model) {
        News news = newsService.findById(id);
        model.addAttribute("id", id);
        model.addAttribute("news", news);
        model.addAttribute("images", news.getImages());
        return "section-article";
    }




    @GetMapping("/admin/podcast")
    public String findAllPodcast(Model model) {
        List<Podcast> podcast = podcastService.findAll();
        model.addAttribute("podcast", podcast);
        return "podcast-list";
    }

    @GetMapping("/admin/podcast-create")
    public String createPodcastForm(Podcast podcast) {
        return "podcast-create";
    }

    @PostMapping("/admin/podcast-create")
    public String createPodcast(@RequestParam("files") MultipartFile[] files, Podcast podcast) throws IOException {
        podcastService.savePodcast(podcast, files);
        return "redirect:/admin/podcast/" + podcast.getId();
    }

    @GetMapping("/admin/podcast-delete/{id}")
    public String deletePodcast(@PathVariable("id") Long id) {
        podcastService.deleteById(id);
        return "redirect:/admin/podcast";
    }

    @GetMapping("/admin/podcast-update/{id}")
    public String updatePodcastForm(@PathVariable("id") Long id, Model model) {
        Podcast podcast = podcastService.findById(id);
        model.addAttribute("podcast", podcast);
        model.addAttribute("images", podcast.getImages());
        return "podcast-update";
    }
    @PostMapping("/admin/podcast-update")
    public String updatePodcast(@RequestParam("files") MultipartFile[] files, Podcast podcast) throws IOException {
        podcastService.deleteImages(podcast, files);
        podcastService.savePodcast(podcast, files);
        return "redirect:/admin/podcast/" + podcast.getId();
    }

    @GetMapping("/admin/podcast/{id}")
    public String podcastDetails(@PathVariable Long id, Model model) {
        Podcast podcast = podcastService.findById(id);
        model.addAttribute("id", id);
        model.addAttribute("podcast", podcast);
        model.addAttribute("images", podcast.getImages());
        return "podcast-details";
    }
    private final ArticleRepository articleRepository;
    private final NewsRepository newsRepository;
    private final PodcastRepository podcastRepository;

    @GetMapping("/search")
    public String searchResults(@RequestParam("searchTerm") String searchTerm, Model model) {
        List<Article> articles = articleRepository.findByTitleContainingIgnoreCase(searchTerm);
        List<News> news = newsRepository.findByTitleContainingIgnoreCase(searchTerm);
        List<Podcast> podcasts = podcastRepository.findByTitleContainingIgnoreCase(searchTerm);
        model.addAttribute("articles", articles);
        model.addAttribute("news", news);
        model.addAttribute("podcasts", podcasts);
        return "search";
    }

}


