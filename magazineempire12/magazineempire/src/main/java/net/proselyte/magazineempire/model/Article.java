package net.proselyte.magazineempire.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "articles")
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "subtitle")
    private String subtitle;
    @Column(name = "text", columnDefinition = "TEXT")
    private String text;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
    mappedBy = "article")
    private List<Image> images = new ArrayList<>();
    @Column(name = "preview_image_id")
    private Long previewImageId;
    @Column(name = "date_of_created")
    private LocalDateTime dateOfCreated;
    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }
    public void addImageToArticle(Image image){
        image.setArticle(this);
        images.add(image);
    }
    public Category getCategory() {
        return category;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "selected_category_id")
    private Long selectedCategoryId;
}

