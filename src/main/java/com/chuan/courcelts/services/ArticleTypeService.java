package com.chuan.courcelts.services;

import com.chuan.courcelts.models.StatusReturn;
import com.chuan.courcelts.models.entities.ArticleType;
import com.chuan.courcelts.repositories.dbcontexts.DbContext;
import com.chuan.courcelts.services.paginators.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleTypeService implements IArticleTypeService{
    @Autowired
    public DbContext dbContext;
    @Autowired
    ThemeService themeService;

    @Override
    public List<ArticleType> findAll(){
        return dbContext.articleTypeRepository.findAll();
    }

    @Override
    public List<ArticleType> pagingFindAll(int pageNum, int sizeOf){
        return (new Paginator<ArticleType>(findAll()))
                .pageOf(pageNum, sizeOf);
    }

    @Override
    public StatusReturn add(ArticleType articleType){
        if(articleType.getArticleTypeId() > 0 && dbContext.articleTypeRepository.existsById(articleType.getArticleTypeId()))
            return StatusReturn.CONFLICT;
        dbContext.articleTypeRepository.save(articleType);
        return StatusReturn.SUCCESS;
    }

    @Override
    public StatusReturn update(ArticleType articleType){
        if(articleType.getArticleTypeId() <= 0) return StatusReturn.IS_EMPTY;
        if(!dbContext.articleTypeRepository.existsById(articleType.getArticleTypeId()))
            return StatusReturn.IS_NOT_FOUND;
        if(articleType.getArticleTypeName() != null) dbContext.articleTypeRepository.save(articleType);
        return StatusReturn.SUCCESS;
    }

    @Override
    public StatusReturn delete(int articleTypeId, boolean isDelete){
        if(!dbContext.articleTypeRepository.existsById(articleTypeId)) return StatusReturn.IS_NOT_FOUND;
        if(dbContext.themeRepository.counterThemeWithArticleTypeId(articleTypeId) > 0){
            if(!isDelete) return StatusReturn.INVALID2;
            dbContext.themeRepository.findAll().stream()
                    .filter(theme -> theme.getArticleType().getArticleTypeId() == articleTypeId)
                    .map(theme -> {
                        themeService.delete(theme.getThemeId(), isDelete);
                        return null;
                    })
                    .collect(Collectors.toList());
        }

        dbContext.articleTypeRepository.deleteById(articleTypeId);
        return StatusReturn.SUCCESS;
    }
}
