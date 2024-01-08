package com.chuan.courcelts.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_articles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article { // Bai viet
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleId;
    @Column(length = 50)
    private String articleName;
    @Column(length = 50)
    private String authorName;
    private String content;
    @Column(length = 1000)
    private String shortContent;
    private LocalDateTime creationTime;
    private String image;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "themeId")
    private Theme theme;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private Account account;
}
