package net.proselyte.magazineempire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import net.proselyte.magazineempire.model.Category;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>{

    List<Category> findAll();
    Category findByName(String name);
}
