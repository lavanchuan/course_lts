package com.chuan.courcelts.repositories;

import com.chuan.courcelts.models.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IArticleRepository extends JpaRepository<Article, Integer> {

    @Query(value = "select count(articleId) from tb_articles where accountId = :accountId",nativeQuery = true)
    int counterArticleWithAccountId(@Param("accountId") int accountId);

    @Query(value = "select count(articleId) from tb_articles where themeId = :themeId",nativeQuery = true)
    int counterArticleWithThemeId(@Param("themeId") int themeId);
}
