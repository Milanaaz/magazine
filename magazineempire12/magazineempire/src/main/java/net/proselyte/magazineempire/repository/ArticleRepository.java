package net.proselyte.magazineempire.repository;

import net.proselyte.magazineempire.model.Article;
import net.proselyte.magazineempire.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByTitle(String title);
    List<Article> findByTitleContainingIgnoreCase(String searchTerm);

    List<Article> findByCategoryId(Long categoryId);

    List<Article> findByCategory(Category category);
}


