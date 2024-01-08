package com.chuan.courcelts.services;

import com.chuan.courcelts.advices.exceptions.CourceTypeNotFoundException;
import com.chuan.courcelts.models.dtos.CourceTypeDTO;
import com.chuan.courcelts.models.entities.CourceType;
import com.chuan.courcelts.repositories.dbcontexts.DbContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourceTypeService implements ICourceTypeService{
    @Autowired
    public DbContext dbContext;

    @Override
    public CourceType courceTypeAdd(String name) {
        return dbContext.courceTypeRepository.save(new CourceType(name));
    }

    @Override
    public CourceType courceTypeUpdate(CourceTypeDTO dto){
        if(dto.getCourceTypeId() == -1 || dto.getCourceTypeId() == 0) return null;
        return dbContext.courceTypeRepository.findById(dto.getCourceTypeId())
                .map(courceType -> {
                    courceType.setCourceTypeName(dto.getCourceName());
                    return dbContext.courceTypeRepository.save(courceType);
                })
                .orElseThrow(()->new CourceTypeNotFoundException(dto.getCourceTypeId()));
    }

    @Override
    public boolean courceTypeDelete(int id, boolean isDelete){
        if(!dbContext.courceTypeRepository.existsById(id))
            return false;
        // TODO delete from tables references (table Register)
        // Cource table references
        int countRecords = dbContext.courceRepository.countRecords(id);
        if(countRecords > 0 && isDelete){
            dbContext.courceRepository.deleteCourcesByCourceTypeId(id);
            dbContext.courceTypeRepository.deleteById(id);
            return true;
        } else if(countRecords > 0 && !isDelete) return false;

        dbContext.courceTypeRepository.deleteById(id);
        return true;
    }
}
