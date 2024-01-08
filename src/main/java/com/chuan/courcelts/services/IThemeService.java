package com.chuan.courcelts.services;

import com.chuan.courcelts.models.StatusReturn;
import com.chuan.courcelts.models.dtos.ThemeDTO;

import java.util.List;

public interface IThemeService {
    List<ThemeDTO> findAll();

    List<ThemeDTO> pagingFindAll(int pageNum, int sizeOf);

    StatusReturn add(ThemeDTO dto);

    StatusReturn update(ThemeDTO dto);

    StatusReturn delete(int themeId, boolean isDelete);
}
