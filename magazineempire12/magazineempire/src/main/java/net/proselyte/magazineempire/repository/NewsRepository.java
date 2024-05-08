package net.proselyte.magazineempire.repository;

import net.proselyte.magazineempire.model.Article;
import net.proselyte.magazineempire.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByTitle(String title);
    List<News> findByTitleContainingIgnoreCase(String searchTerm);

    List<News> findAll();
    void deleteById(Long id);
}
