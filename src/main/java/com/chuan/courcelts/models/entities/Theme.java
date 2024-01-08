package com.chuan.courcelts.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_themes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Theme { // Chu de
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int themeId;
    @Column(length = 50)
    private String themeName;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "articleTypeId")
    private ArticleType articleType;
}
