package com.chuan.courcelts.services;

import com.chuan.courcelts.models.StatusReturn;
import com.chuan.courcelts.models.dtos.ArticleDTO;
import com.chuan.courcelts.repositories.dbcontexts.DbContext;
import com.chuan.courcelts.services.mappers.ArticleMapper;
import com.chuan.courcelts.services.paginators.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService implements IArticleService{

    @Autowired
    public DbContext dbContext;

    @Override
    public List<ArticleDTO> findAll(){
        return dbContext.articleRepository.findAll()
                .stream().map(ArticleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ArticleDTO> findByArticleName(String articleName){
        return findAll().stream().filter(dto -> dto.getArticleName().contains(articleName))
                .collect(Collectors.toList());
    }

    public List<ArticleDTO> pagingFindAll(int pageNum, int sizeOf){
        return new Paginator<ArticleDTO>(findAll())
                .pageOf(pageNum, sizeOf);
    }

    public List<ArticleDTO> pagingFindByArticleName(String articleName, int pageNum, int sizeOf){
        return (new Paginator<ArticleDTO>(findByArticleName(articleName)))
                .pageOf(pageNum, sizeOf);
    }

    public StatusReturn add(ArticleDTO dto){
        if(dto.getThemeId() <= 0
        || dto.getAccountId() <= 0
        || dto.getArticleName() == null
        || dto.getAuthorName() == null
        || dto.getContent() == null
        || dto.getShortContent() == null
        || dto.getImage() == null) return StatusReturn.IS_EMPTY;
        if(dbContext.articleRepository.existsById(dto.getArticleId()))
            return StatusReturn.CONFLICT;
        if(!dbContext.accountRepository.existsById(dto.getAccountId())
        ||!dbContext.themeRepository.existsById(dto.getThemeId()))
            return StatusReturn.IS_NOT_FOUND;
        if(dto.getCreationTime() == null) dto.setCreationTime(LocalDateTime.now());

        dbContext.articleRepository.save(ArticleMapper.toEntity(dto, dbContext));
        return StatusReturn.SUCCESS;
    }

    @Override
    public StatusReturn update(ArticleDTO dto){
        if(dto.getArticleId() <= 0) return StatusReturn.IS_EMPTY;
        if(!dbContext.articleRepository.existsById(dto.getArticleId())) return StatusReturn.IS_NOT_FOUND;
        if(dto.getThemeId() > 0 && !dbContext.themeRepository.existsById(dto.getThemeId()) ||
        dto.getAccountId() > 0 && !dbContext.accountRepository.existsById(dto.getAccountId()))
            return StatusReturn.IS_NOT_FOUND;

        dbContext.articleRepository.findById(dto.getArticleId())
                .map(article -> {
                    if(dto.getArticleName() != null) article.setArticleName(dto.getArticleName());
                    if(dto.getAuthorName() != null) article.setAuthorName(dto.getAuthorName());
                    if(dto.getContent() != null) article.setContent(dto.getContent());
                    if(dto.getShortContent() != null) article.setShortContent(dto.getShortContent());
                    if(dto.getImage() != null) article.setImage(dto.getImage());
                    if(dto.getThemeId() > 0) article.setTheme(dbContext.themeRepository.findById(dto.getThemeId()).orElseThrow());
                    if(dto.getAccountId() > 0) article.setAccount(dbContext.accountRepository.findById(dto.getAccountId()).orElseThrow());
                    if(dto.getCreationTime() != null) article.setCreationTime(dto.getCreationTime());
                    return dbContext.articleRepository.save(article);
                })
                .orElseThrow();
        return StatusReturn.SUCCESS;
    }

    @Override
    public StatusReturn delete(int articleId){
        if(!dbContext.articleRepository.existsById(articleId))
            return StatusReturn.IS_NOT_FOUND;
        dbContext.articleRepository.deleteById(articleId);
        return StatusReturn.SUCCESS;
    }
}
