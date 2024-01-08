package com.chuan.courcelts.repositories;

import com.chuan.courcelts.models.entities.CourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourceTypeRepository extends JpaRepository<CourceType, Integer> {
}
