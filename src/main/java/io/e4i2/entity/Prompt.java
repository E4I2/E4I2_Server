package io.e4i2.entity;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "prompt")
@Getter
public class Prompt {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROMPT_ID")
    private Integer promptId;
    @Column(name = "MBTI")
    @Enumerated(EnumType.STRING)
    private Mbti mbti;
    @Column(name = "DEFAULT_PROMPT", length = 512)
    private String defaultPrompt;
    
}
