package com.chuan.courcelts.repositories;

import com.chuan.courcelts.models.entities.StatusStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStatusStudyRepository extends JpaRepository<StatusStudy, Integer> {
}
