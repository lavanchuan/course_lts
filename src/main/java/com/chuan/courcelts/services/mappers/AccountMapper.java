package com.chuan.courcelts.services.mappers;

import com.chuan.courcelts.advices.exceptions.AuthorityNotFoundException;
import com.chuan.courcelts.models.dtos.AccountDTO;
import com.chuan.courcelts.models.entities.Account;
import com.chuan.courcelts.repositories.dbcontexts.DbContext;

public class AccountMapper {
    public static AccountDTO toDTO(Account entity){
        return new AccountDTO(entity.getAccountId(),
                entity.getFullname(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getAuthority() == null ? -1 : entity.getAuthority().getAuthorityId());
    }

    public static Account toEntity(AccountDTO dto, DbContext dbContext){
        return new Account(
                dto.getAccountId(),
                dto.getFullname(),
                dto.getUsername(),
                dto.getPassword(),
                dto.getAuthorityId() <= 0 ? null : dbContext.authorityRepository.findById(dto.getAuthorityId())
                        .orElseThrow(()->new AuthorityNotFoundException(dto.getAuthorityId()))
        );
    }
}
