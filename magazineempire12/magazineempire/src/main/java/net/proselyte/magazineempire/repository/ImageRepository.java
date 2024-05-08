package net.proselyte.magazineempire.repository;

import net.proselyte.magazineempire.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    void deleteByArticleId(Long userId);
    void deleteByNewsId(Long id);

}

