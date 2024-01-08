package com.chuan.courcelts.services;

import com.chuan.courcelts.models.StatusReturn;
import com.chuan.courcelts.models.entities.ArticleType;

import java.util.List;

public interface IArticleTypeService {
    List<ArticleType> findAll();

    List<ArticleType> pagingFindAll(int pageNum, int sizeOf);

    StatusReturn add(ArticleType articleType);

    StatusReturn update(ArticleType articleType);

    StatusReturn delete(int articleTypeId, boolean isDelete);
}
