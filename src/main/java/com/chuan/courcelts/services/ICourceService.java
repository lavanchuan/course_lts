package com.chuan.courcelts.services;

import com.chuan.courcelts.models.dtos.CourceDTO;
import com.chuan.courcelts.models.entities.Cource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ICourceService {
    List<CourceDTO> findAll();

    CourceDTO add(CourceDTO dto);

    CourceDTO update(int courceId, CourceDTO dto);

    boolean delete(int courceId, boolean isDelete);

    List<CourceDTO> pagingFindAll(int pageNum, int sizeOf);

    List<CourceDTO> pagingFindByName(String courceName, int pageNum, int sizeOf);
}
