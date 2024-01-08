package com.chuan.courcelts.services.mappers;

import com.chuan.courcelts.advices.exceptions.AccountNotFoundException;
import com.chuan.courcelts.advices.exceptions.ThemeNotFoundException;
import com.chuan.courcelts.models.dtos.ArticleDTO;
import com.chuan.courcelts.models.entities.Article;
import com.chuan.courcelts.repositories.dbcontexts.DbContext;

public class ArticleMapper {
    public static ArticleDTO toDTO(Article entity){
        return new ArticleDTO(entity.getArticleId(),
                entity.getArticleName(),
                entity.getAuthorName(),
                entity.getContent(),
                entity.getShortContent(),
                entity.getCreationTime() == null ? null : entity.getCreationTime(),
                entity.getImage(),
                entity.getTheme() == null ? -1 : entity.getTheme().getThemeId(),
                entity.getAccount() == null ? -1 : entity.getAccount().getAccountId());
    }

    public static Article toEntity(ArticleDTO dto, DbContext dbContext){
        return new Article(dto.getArticleId(),
                dto.getArticleName(),
                dto.getAuthorName(),
                dto.getContent(),
                dto.getShortContent(),
                dto.getCreationTime(),
                dto.getImage(),
                dto.getThemeId() <= 0 ? null : dbContext.themeRepository.findById(dto.getThemeId())
                        .orElseThrow(()->new ThemeNotFoundException(dto.getThemeId())),
                dto.getAccountId() <= 0 ? null : dbContext.accountRepository.findById(dto.getAccountId())
                        .orElseThrow(()->new AccountNotFoundException(dto.getAccountId())));
    }
}
