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
    @Column(name = "description")
    private String description;
    @Column(name = "thumbnail")
    private String thumbnail;
}
