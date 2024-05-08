package net.proselyte.magazineempire.service;

import lombok.RequiredArgsConstructor;
import net.proselyte.magazineempire.model.News;
import net.proselyte.magazineempire.repository.ImageRepository;
import net.proselyte.magazineempire.model.Image;
import net.proselyte.magazineempire.repository.NewsRepository;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;


import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Transactional
public class NewsService {
    private final NewsRepository newsRepository;
    private final ImageRepository imageRepository;
    public List<News> listNews(String title) {
        if (title != null) return newsRepository.findByTitle(title);
        return newsRepository.findAll();
    }

    public News findById(Long id){
        return newsRepository.getOne(id);
    }

    public List<News> findAll(){
        return newsRepository.findAll();
    }
    public void saveNews(News news, MultipartFile[] files) throws IOException {
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file.getSize() != 0) {
                Image image = toImageEntity(file);
                image.setNews(news);
                news.addImageToNews(image);
                if (i == 0) {
                    image.setPreviewImage(true);
                }
            }
        }
        log.info("Saving new News. Title: {}; Date: {}; Text: {}", news.getTitle(), news.getDate(), news.getText());
        News newsFromDb = newsRepository.save(news);
        newsFromDb.setPreviewImageId(newsFromDb.getImages().get(0).getId());
        newsRepository.save(newsFromDb);
    }

    public void deleteById(Long id){
        newsRepository.deleteById(id);
    }

    public void deleteImages(News news, MultipartFile[] files){
        imageRepository.deleteByNewsId(news.getId());
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

}