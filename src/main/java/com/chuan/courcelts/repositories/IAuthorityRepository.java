package com.chuan.courcelts.repositories;

import com.chuan.courcelts.models.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthorityRepository extends JpaRepository<Authority, Integer> {
}
