package net.proselyte.magazineempire.repository;

import net.proselyte.magazineempire.model.News;
import net.proselyte.magazineempire.model.Podcast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PodcastRepository extends JpaRepository<Podcast, Long> {
    List<Podcast> findByTitle(String title);
    List<Podcast> findByTitleContainingIgnoreCase(String searchTerm);

    List<Podcast> findAll();
    void deleteById(Long id);
}
