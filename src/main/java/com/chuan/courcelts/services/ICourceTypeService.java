package com.chuan.courcelts.services;

import com.chuan.courcelts.models.dtos.CourceTypeDTO;
import com.chuan.courcelts.models.entities.CourceType;

public interface ICourceTypeService {
    CourceType courceTypeAdd(String name);

    CourceType courceTypeUpdate(CourceTypeDTO dto);

    boolean courceTypeDelete(int id, boolean isDelete);
}
