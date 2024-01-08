package com.chuan.courcelts.services;

import com.chuan.courcelts.models.StatusReturn;
import com.chuan.courcelts.models.dtos.RegisterDTO;

import java.util.List;

public interface IRegisterService {
    List<RegisterDTO> findAll();

    StatusReturn add(RegisterDTO dto);

    StatusReturn update(int registerId, RegisterDTO dto);

    StatusReturn delete(int registerId);

    List<RegisterDTO> pagingFindAll(int pageNum, int sizeOf);
}
