package com.chuan.courcelts.services;

import com.chuan.courcelts.models.StatusReturn;
import com.chuan.courcelts.models.dtos.ThemeDTO;
import com.chuan.courcelts.repositories.dbcontexts.DbContext;
import com.chuan.courcelts.services.mappers.ThemeMapper;
import com.chuan.courcelts.services.paginators.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService implements IThemeService{
    @Autowired
    public DbContext dbContext;

    @Override
    public List<ThemeDTO> findAll(){
        return dbContext.themeRepository.findAll()
                .stream().map(ThemeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ThemeDTO> pagingFindAll(int pageNum, int sizeOf){
        return (new Paginator<ThemeDTO>(findAll()))
                .pageOf(pageNum, sizeOf);
    }

    @Override
    public StatusReturn add(ThemeDTO dto){
        if(dto.getThemeId() > 0 && dbContext.themeRepository.existsById(dto.getThemeId())) return StatusReturn.CONFLICT;
        if(dto.getArticleTypeId() > 0 && !dbContext.articleTypeRepository.existsById(dto.getArticleTypeId())) return StatusReturn.IS_NOT_FOUND;
        dbContext.themeRepository.save(ThemeMapper.toEntity(dto, dbContext));
        return StatusReturn.SUCCESS;
    }

    @Override
    public StatusReturn update(ThemeDTO dto){
        if(dto.getThemeId() > 0 && !dbContext.themeRepository.existsById(dto.getThemeId())) return StatusReturn.IS_NOT_FOUND;
        if(dto.getArticleTypeId() > 0 && !dbContext.articleTypeRepository.existsById(dto.getArticleTypeId())) return StatusReturn.IS_NOT_FOUND;
        dbContext.themeRepository.findById(dto.getThemeId())
                .map(theme -> {
                    if(dto.getThemeName() != null) theme.setThemeName(dto.getThemeName());
                    if(dto.getContent() != null) theme.setContent(dto.getContent());
                    if(dto.getArticleTypeId() > 0) theme.setArticleType(dbContext.articleTypeRepository.findById(dto.getArticleTypeId()).orElseThrow());
                    return dbContext.themeRepository.save(theme);
                }).orElseThrow();
        return StatusReturn.SUCCESS;
    }

    @Override
    public StatusReturn delete(int themeId, boolean isDelete){
        if(!dbContext.themeRepository.existsById(themeId)) return StatusReturn.IS_NOT_FOUND;
        if(dbContext.articleRepository.counterArticleWithThemeId(themeId) > 0){
            if(!isDelete) return StatusReturn.INVALID2;
            dbContext.articleRepository.findAll()
                    .stream().filter(article -> article.getTheme().getThemeId() == themeId)
                    .map(article -> {
                        dbContext.articleRepository.deleteById(article.getArticleId());
                        return null;
                    }).collect(Collectors.toList());
        }

        dbContext.themeRepository.deleteById(themeId);
        return StatusReturn.SUCCESS;
    }

}
