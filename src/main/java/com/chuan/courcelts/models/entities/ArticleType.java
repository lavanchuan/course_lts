package com.chuan.courcelts.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_article_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleType { // Loai bai viet
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleTypeId;
    @Column(length = 50)
    private String articleTypeName;
}
