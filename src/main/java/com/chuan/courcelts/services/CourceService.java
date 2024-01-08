package com.chuan.courcelts.services;

import com.chuan.courcelts.advices.exceptions.CourceNotFoundException;
import com.chuan.courcelts.advices.exceptions.CourceTypeNotFoundException;
import com.chuan.courcelts.models.dtos.CourceDTO;
import com.chuan.courcelts.models.entities.Cource;
import com.chuan.courcelts.models.entities.StatusStudy;
import com.chuan.courcelts.repositories.dbcontexts.DbContext;
import com.chuan.courcelts.services.mappers.CourceMapper;
import com.chuan.courcelts.services.paginators.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourceService implements ICourceService {
    @Autowired
    public DbContext dbContext;

    @Override
    public List<CourceDTO> findAll() {
        return dbContext.courceRepository.findAll().stream().map(cource -> {
            return CourceMapper.toDTO(cource);
        }).collect(Collectors.toList());
    }

    @Override
    public CourceDTO add(CourceDTO dto) {
        if (dto.getCourceId() != 0 && dbContext.courceRepository.existsById(dto.getCourceId())) return null;
        return CourceMapper.toDTO(dbContext.courceRepository.save(CourceMapper.toEntity(dto, dbContext)));
    }

    @Override
    public CourceDTO update(int courceId, CourceDTO dto) {
        dto.setCourceId(courceId);
        Cource entity = dbContext.courceRepository.findById(courceId).map(cource -> {
            // if is null(default) -> current
            if (dto.getCourceName() != null)
                cource.setCourceName(dto.getCourceName());
            if (dto.getFees() != 0) cource.setFees(dto.getFees());
            if (dto.getCourceTypeId() != 0) cource.setCourceType(dbContext.courceTypeRepository
                    .findById(dto.getCourceTypeId()).orElseThrow(() -> new CourceTypeNotFoundException(dto.getCourceTypeId())));
            if (dto.getImage() != null) cource.setImage(dto.getImage());
            if (dto.getContent() != null) cource.setContent(dto.getContent());
            if (dto.getIntro() != null) cource.setIntro(dto.getIntro());
            if (dto.getTimer() != 0) cource.setTimer(dto.getTimer());
            if (dto.getTotalStudent() != 0) cource.setTotalStudent(dto.getTotalStudent());
            if (dto.getTotalSubject() != 0) cource.setTotalSubject(dto.getTotalSubject());

            return dbContext.courceRepository.save(cource);
        }).orElseThrow(() -> new CourceNotFoundException(courceId));

        return CourceMapper.toDTO(entity);
    }

    @Override
    public boolean delete(int courceId, boolean isDelete) {
        if (!dbContext.courceRepository.existsById(courceId))
            return false;
        // toDo delete from tables references
        int countRecords = dbContext.registerRepository.countRecords(courceId);

        if (countRecords > 0 && isDelete) {
            dbContext.registerRepository.deleteRegistersByCourceId(courceId);
            dbContext.courceRepository.deleteById(courceId);
            return true;
        } else if (countRecords > 0 && !isDelete) return false;

        dbContext.courceRepository.deleteById(courceId);
        return true;
    }

    public List<CourceDTO> findCourceDTOsByName(String courceName) {
        return dbContext.courceRepository.findCourceListByName(courceName)
                .stream().map(CourceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourceDTO> pagingFindAll(int pageNum, int sizeOf) {
        Paginator<CourceDTO> paginator = new Paginator<>(findAll());
        return paginator.pageOf(pageNum, sizeOf);
    }

    @Override
    public List<CourceDTO> pagingFindByName(String courceName, int pageNum, int sizeOf) {
        Paginator<CourceDTO> paginator = new Paginator<>(findCourceDTOsByName(courceName));
        return paginator.pageOf(pageNum, sizeOf);
    }

    // update student quantity in register

}
