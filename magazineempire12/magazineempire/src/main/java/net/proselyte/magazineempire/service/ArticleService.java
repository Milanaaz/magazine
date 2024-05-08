package net.proselyte.magazineempire.service;

import lombok.RequiredArgsConstructor;
import net.proselyte.magazineempire.model.Category;
import net.proselyte.magazineempire.repository.ArticleRepository;
import net.proselyte.magazineempire.repository.ImageRepository;
import net.proselyte.magazineempire.model.Article;
import net.proselyte.magazineempire.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;


import javax.persistence.Id;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ImageRepository imageRepository;
    public List<Article> listArticles(String title) {
        if (title != null) return articleRepository.findByTitle(title);
        return articleRepository.findAll();
    }

    public Article findById(Long id){
        return articleRepository.getOne(id);
    }

    public List<Article> findAll(){
        return articleRepository.findAll();
    }
    public void saveArticle(Article article, MultipartFile[] files) throws IOException {
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file.getSize() != 0) {
                Image image = toImageEntity(file);
                image.setArticle(article);
                article.addImageToArticle(image);
                if (i == 0) {
                    image.setPreviewImage(true);
                }
            }
        }
        log.info("Saving new Article. Title: {}; Subtitle: {}; Text: {}", article.getTitle(), article.getSubtitle(), article.getText());
        Article articleFromDb = articleRepository.save(article);
        articleFromDb.setPreviewImageId(articleFromDb.getImages().get(0).getId());
        articleRepository.save(articleFromDb);
    }
    public void deleteById(Long id){
        articleRepository.deleteById(id);
    }

    public void deleteImages(Article article, MultipartFile[] files){
        imageRepository.deleteByArticleId(article.getId());
    }
    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }
    public List<Article> getArticlesByCategoryId(Long categoryId) {
        return articleRepository.findByCategoryId(categoryId);
    }
    public Article getArticleById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }


}