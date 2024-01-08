package com.chuan.courcelts.services;

import com.chuan.courcelts.models.StatusReturn;
import com.chuan.courcelts.models.entities.Authority;

import java.util.List;

public interface IAuthorityService {
    List<Authority> findAll();

    List<Authority> pagingFindAll(int pageNum, int sizeOf);

    StatusReturn add(Authority authority);

    StatusReturn update(Authority authority);

    StatusReturn delete(int authorityId, boolean isDelete);
}
