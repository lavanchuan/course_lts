package com.chuan.courcelts.services.mappers;

import com.chuan.courcelts.advices.exceptions.AccountNotFoundException;
import com.chuan.courcelts.advices.exceptions.CourceNotFoundException;
import com.chuan.courcelts.advices.exceptions.StatusStudyNotFoundException;
import com.chuan.courcelts.advices.exceptions.StudentNotFoundException;
import com.chuan.courcelts.models.dtos.RegisterDTO;
import com.chuan.courcelts.models.entities.Register;
import com.chuan.courcelts.repositories.dbcontexts.DbContext;

public class RegisterMapper {
    public static RegisterDTO toDTO(Register entity) {
        if (entity == null) return null;
        return new RegisterDTO(entity.getRegisterId(),
                entity.getCource() == null ? -1 : entity.getCource().getCourceId(),
                entity.getStudent() == null ? -1 : entity.getStudent().getStudentId(),
                entity.getRegisDate(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getStatusStudy() == null ? -1 : entity.getStatusStudy().getStatusId(),
                entity.getAccount() == null ? -1 : entity.getAccount().getAccountId());
    }

    public static Register toEntity(RegisterDTO dto, DbContext dbContext) {
//        return new Register(dto.getRegisterId(),
//                dto.getCourceId() <= 0 ? null : !dbContext.courceRepository.existsById(dto.getCourceId()) ? null : dbContext.courceRepository.findById(dto.getCourceId()).orElseThrow(() -> new CourceNotFoundException(dto.getCourceId())),
//                dto.getStudentId() <= 0 ? null : !dbContext.studentRepository.existsById(dto.getStudentId()) ? null : dbContext.studentRepository.findById(dto.getStudentId()).orElseThrow(() -> new StudentNotFoundException(dto.getStudentId())),
//                dto.getRegisDate() == null ? null : dto.getRegisDate(),
//                dto.getStartDate() == null ? null : dto.getStartDate(),
//                dto.getEndDate() == null ? null : dto.getEndDate(),
//                dto.getStatusId() <= 0 ? null : !dbContext.statusRepository.existsById(dto.getStatusId()) ? null : dbContext.statusRepository.findById(dto.getStatusId()).orElseThrow(() -> new StatusStudyNotFoundException(dto.getStatusId())),
//                dto.getAccountId() <= 0 ? null : !dbContext.accountRepository.existsById(dto.getStatusId()) ? null : dbContext.accountRepository.findById(dto.getAccountId()).orElseThrow(() -> new AccountNotFoundException(dto.getAccountId())));

        return new Register(dto.getRegisterId(),
                dto.getCourceId() <= 0 ? null : dbContext.courceRepository.findById(dto.getCourceId()).orElseThrow(()->new CourceNotFoundException(dto.getCourceId())),
                dto.getStudentId() <= 0 ? null : dbContext.studentRepository.findById(dto.getStudentId()).orElseThrow(()->new StudentNotFoundException(dto.getStudentId())),
                dto.getRegisDate() == null ? null : dto.getRegisDate(),
                dto.getStartDate() == null ? null : dto.getStartDate(),
                dto.getEndDate() == null ? null : dto.getEndDate(),
                dto.getStatusId() <= 0 ? null : dbContext.statusRepository.findById(dto.getStatusId()).orElseThrow(()->new StatusStudyNotFoundException(dto.getStatusId())),
                dto.getAccountId() <= 0 ? null : dbContext.accountRepository.findById(dto.getAccountId()).orElseThrow(()->new AccountNotFoundException(dto.getAccountId())));


    }
}
