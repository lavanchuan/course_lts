package com.chuan.courcelts.models.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {
    @NotNull
    private int articleId;
    @Size(max = 50)
    @NotNull
    private String articleName;
    @Size(max = 50)
    @NotNull
    private String authorName;
    @NotNull
    private String content;
    @Size(max = 1000)
    @NotNull
    private String shortContent;
    @NotNull
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private LocalDateTime creationTime;
    @NotNull
    private String image;
    @NotNull
    private int themeId;
    @NotNull
    private int accountId;
}
