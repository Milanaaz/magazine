package net.proselyte.magazineempire.service;

import lombok.RequiredArgsConstructor;
import net.proselyte.magazineempire.model.Podcast;
import net.proselyte.magazineempire.repository.ImageRepository;
import net.proselyte.magazineempire.model.Image;
import net.proselyte.magazineempire.repository.PodcastRepository;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;


import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Transactional
public class PodcastService {
    private final PodcastRepository podcastRepository;
    private final ImageRepository imageRepository;
    public List<Podcast> listPodcast(String title) {
        if (title != null) return podcastRepository.findByTitle(title);
        return podcastRepository.findAll();
    }

    public Podcast findById(Long id){
        return podcastRepository.getOne(id);
    }

    public List<Podcast> findAll(){
        return podcastRepository.findAll();
    }
    public void savePodcast(Podcast podcast, MultipartFile[] files) throws IOException {
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file.getSize() != 0) {
                Image image = toImageEntity(file);
                image.setPodcast(podcast);
                podcast.addImageToNews(image);
                if (i == 0) {
                    image.setPreviewImage(true);
                }
            }
        }
        log.info("Saving new News. Title: {}; Album id: {}; Text: {}", podcast.getTitle(), podcast.getAlbumId(), podcast.getText());
        Podcast podcastFromDb = podcastRepository.save(podcast);
        podcastFromDb.setPreviewImageId(podcastFromDb.getImages().get(0).getId());
        podcastRepository.save(podcastFromDb);
    }

    public void deleteById(Long id){
        podcastRepository.deleteById(id);
    }

    public void deleteImages(Podcast podcast, MultipartFile[] files){
        imageRepository.deleteByNewsId(podcast.getId());
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