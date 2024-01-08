package com.chuan.courcelts.services.mappers;

import com.chuan.courcelts.advices.exceptions.ArticleTypeNotFoundException;
import com.chuan.courcelts.models.dtos.ThemeDTO;
import com.chuan.courcelts.models.entities.Theme;
import com.chuan.courcelts.repositories.dbcontexts.DbContext;

public class ThemeMapper {
    public static ThemeDTO toDTO(Theme entity){
        return new ThemeDTO(entity.getThemeId(),
                entity.getThemeName(),
                entity.getContent(),
                entity.getArticleType() == null ? -1 : entity.getArticleType().getArticleTypeId());
    }

    public static Theme toEntity(ThemeDTO dto, DbContext dbContext){
        return new Theme(dto.getThemeId(),
                dto.getThemeName(),
                dto.getContent(),
                dto.getArticleTypeId() <= 0 ? null : dbContext.articleTypeRepository.findById(dto.getArticleTypeId())
                        .orElseThrow(()->new ArticleTypeNotFoundException(dto.getArticleTypeId())));
    }
}
