package com.chuan.courcelts.repositories;

import com.chuan.courcelts.models.entities.ArticleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IArticleTypeRepository extends JpaRepository<ArticleType, Integer> {
}
