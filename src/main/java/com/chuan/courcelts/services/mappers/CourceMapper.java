package com.chuan.courcelts.services.mappers;

import com.chuan.courcelts.advices.exceptions.CourceTypeNotFoundException;
import com.chuan.courcelts.models.dtos.CourceDTO;
import com.chuan.courcelts.models.entities.Cource;
import com.chuan.courcelts.repositories.dbcontexts.DbContext;

public class CourceMapper {
    public static CourceDTO toDTO(Cource entity){
        return new CourceDTO(entity.getCourceId(),
                entity.getCourceName(),
                entity.getTimer(),
                entity.getIntro(),
                entity.getContent(),
                entity.getFees(),
                entity.getTotalStudent(),
                entity.getTotalSubject(),
                entity.getImage(),
                entity.getCourceType()==null?-1:entity.getCourceType().getCourceTypeId());
    }

    public static Cource toEntity(CourceDTO dto, DbContext dbContext){
        return new Cource(dto.getCourceId(),
                dto.getCourceName(),
                dto.getTimer(),
                dto.getIntro(),
                dto.getContent(),
                dto.getFees(),
                dto.getTotalStudent(),
                dto.getTotalSubject(),
                dto.getImage(),
                dbContext.courceTypeRepository.findById(dto.getCourceTypeId())
                        .orElseThrow(()->new CourceTypeNotFoundException(dto.getCourceTypeId())));
    }
}
