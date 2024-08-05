package io.e4i2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "content_prompt")
public class ContentPrompt {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contentPromptId;
    @Column(name = "CONTENT_PROMPT")
    private String contentPromptTitle;
    @Column(name = "CONTENT_TYPE")
    private String contentType;
}
