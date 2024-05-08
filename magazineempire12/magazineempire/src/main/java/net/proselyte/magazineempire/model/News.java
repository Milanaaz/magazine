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
@Table(name = "news")
@AllArgsConstructor
@NoArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "date")
    private String date;
    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "news")
    private List<Image> images = new ArrayList<>();
    @Column(name = "preview_image_id")
    private Long previewImageId;
    @Column(name = "date_of_created")
    private LocalDateTime dateOfCreated;
    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }

    public void addImageToNews(Image image){
        image.setNews(this);
        images.add(image);
    }
}
