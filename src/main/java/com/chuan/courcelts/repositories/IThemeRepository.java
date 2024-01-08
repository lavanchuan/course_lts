package com.chuan.courcelts.repositories;

import com.chuan.courcelts.models.entities.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IThemeRepository extends JpaRepository<Theme, Integer> {
    @Query(value = "select count(themeId) from tb_themes where articleTypeId = :articleTypeId",
    nativeQuery = true)
    int counterThemeWithArticleTypeId(@Param("articleTypeId") int articleTypeId);
}
