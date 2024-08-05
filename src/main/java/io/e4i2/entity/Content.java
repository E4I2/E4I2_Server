package io.e4i2.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "content")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contentId;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "CONTENT_TITLE")
    private String contentTitle;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "THUMBNAIL")
    private String thumbnail;
    @Column(name = "IMAGE_URL")
    private String imageUrl; // 추가된 필드
}
