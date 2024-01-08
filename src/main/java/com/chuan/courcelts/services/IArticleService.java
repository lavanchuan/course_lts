package com.chuan.courcelts.services;

import com.chuan.courcelts.models.StatusReturn;
import com.chuan.courcelts.models.dtos.ArticleDTO;

import java.util.List;

public interface IArticleService {
    List<ArticleDTO> findAll();

    StatusReturn update(ArticleDTO dto);

    StatusReturn delete(int articleId);
}
