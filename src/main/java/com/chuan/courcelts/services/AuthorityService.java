package com.chuan.courcelts.services;

import com.chuan.courcelts.advices.exceptions.AuthorityNotFoundException;
import com.chuan.courcelts.models.StatusReturn;
import com.chuan.courcelts.models.entities.Authority;
import com.chuan.courcelts.repositories.dbcontexts.DbContext;
import com.chuan.courcelts.services.paginators.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorityService implements IAuthorityService{
    @Autowired
    public DbContext dbContext;
    @Autowired
    AccountService accountService;

    @Override
    public List<Authority> findAll(){
        return dbContext.authorityRepository.findAll();
    }

    @Override
    public List<Authority> pagingFindAll(int pageNum, int sizeOf){
        return (new Paginator<Authority>(dbContext.authorityRepository.findAll())).pageOf(pageNum, sizeOf);
    }

    @Override
    public StatusReturn add(Authority authority){
        if(authority.getAuthorityId() > 0 && dbContext.authorityRepository.existsById(authority.getAuthorityId()))
            return StatusReturn.CONFLICT;
        dbContext.authorityRepository.save(authority);
        return StatusReturn.SUCCESS;
    }

    @Override
    public StatusReturn update(Authority authority){
        dbContext.authorityRepository.findById(authority.getAuthorityId())
                .map(authority1 -> {
                    authority1.setAuthorityName(authority.getAuthorityName());
                    return dbContext.authorityRepository.save(authority1);
                }).orElseThrow(()->new AuthorityNotFoundException(authority.getAuthorityId()));
        return StatusReturn.SUCCESS;
    }

    @Override
    public StatusReturn delete(int authorityId, boolean isDelete){
        if(!dbContext.authorityRepository.existsById(authorityId))
            return StatusReturn.IS_NOT_FOUND;
        // todo delete account
        if(dbContext.accountRepository.counterAccountWithAuthorityId(authorityId) > 0){
            if(!isDelete)
                return StatusReturn.INVALID2;
            dbContext.accountRepository.findAll().stream()
                    .filter(account -> account.getAuthority().getAuthorityId() == authorityId)
                    .map(account -> {
                        accountService.delete(account.getAccountId(), isDelete);
                        return null;
                    }).collect(Collectors.toList());
        }

        dbContext.authorityRepository.deleteById(authorityId);

        return StatusReturn.SUCCESS;
    }
}
