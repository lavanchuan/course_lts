package com.chuan.courcelts.services.mappers;

import com.chuan.courcelts.models.dtos.CourceTypeDTO;
import com.chuan.courcelts.models.entities.CourceType;

public class CourceTypeMapper {
    public static CourceTypeDTO toDTO(CourceType entity){
        return new CourceTypeDTO(entity.getCourceTypeId()==0 ? -1 : entity.getCourceTypeId(), entity.getCourceTypeName());
    }
}
