package com.chuan.courcelts.services;

import com.chuan.courcelts.models.entities.StatusStudy;
import com.chuan.courcelts.repositories.dbcontexts.DbContext;
import com.chuan.courcelts.services.mappers.RegisterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusService implements IStatusService{
    @Autowired
    public DbContext dbContext;

    @Override
    public List<StatusStudy> findAll(){
        return dbContext.statusRepository.findAll();
    }

    @Override
    public int add(String statusName){
        dbContext.statusRepository.save(new StatusStudy(statusName));
        return SUCCESS;
    }

    @Override
    public int update(StatusStudy statusStudy){
        // check exists id
        if(!dbContext.statusRepository.existsById(statusStudy.getStatusId())) return NOT_FOUND;

        dbContext.statusRepository.save(statusStudy);
        return SUCCESS;
    }

    @Override
    public int delete(int statusId, boolean isDelete){
        // check exists id
        if(!dbContext.statusRepository.existsById(statusId)) return NOT_FOUND;

        // check other table referencing this
        int countRecord = dbContext.registerRepository.findAll().stream()
                .filter(register -> RegisterMapper.toDTO(register).getStatusId() == statusId)
                .collect(Collectors.toList()).size();

        if(countRecord > 0){
            if(!isDelete) return CONFLICT;

            // delete records from register table with statusId
            dbContext.registerRepository.findAll().stream()
                    .filter(register -> RegisterMapper.toDTO(register).getStatusId() == statusId)
                    .map(register -> {
                        dbContext.registerRepository.deleteById(register.getRegisterId());
                        return null;
                    }).collect(Collectors.toList());
            // delete from status table
            dbContext.statusRepository.deleteById(statusId);
            return SUCCESS;
        }

        dbContext.statusRepository.deleteById(statusId);
        return SUCCESS;
    }
}
