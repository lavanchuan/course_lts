package com.chuan.courcelts.services;

import com.chuan.courcelts.models.StatusReturn;
import com.chuan.courcelts.models.dtos.AccountDTO;
import com.chuan.courcelts.models.entities.Account;
import com.chuan.courcelts.repositories.dbcontexts.DbContext;
import com.chuan.courcelts.services.mappers.AccountMapper;
import com.chuan.courcelts.services.paginators.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService implements IAccountService {
    @Autowired
    public DbContext dbContext;

    @Override
    public List<AccountDTO> findAll(){
        return dbContext.accountRepository.findAll()
                .stream().map(AccountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> findByFullname(String fullname){
        return dbContext.accountRepository.findAll().stream()
                .filter(account -> account.getFullname().toLowerCase().indexOf(fullname.toLowerCase()) != -1)
                .map(AccountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> pagingFindByFullname(String fullname, int pageNum, int sizeOf){
        return (new Paginator<AccountDTO>(findByFullname(fullname)))
                .pageOf(pageNum, sizeOf);
    }

    @Override
    public List<AccountDTO> pagingFindAll(int pageNum, int sizeOf){
        return (new Paginator<AccountDTO>(findAll()))
                .pageOf(pageNum, sizeOf);
    }

    @Override
    public StatusReturn add(AccountDTO dto){
        if(dto.getAccountId() > 0 && dbContext.accountRepository.existsById(dto.getAccountId()))
            return StatusReturn.CONFLICT;

        if(dto.getAuthorityId() > 0 && !dbContext.authorityRepository.existsById(dto.getAuthorityId()))
            return StatusReturn.IS_NOT_FOUND;

        if(dto.getUsername() == null || dto.getUsername().isEmpty() ||
        dto.getPassword() == null || dto.getPassword().isEmpty())
            return StatusReturn.IS_EMPTY;

        System.out.println("Unique: " + checkUniqueUsername(dto.getUsername()));
        System.out.println("Password: " + invalidPassword(dto.getPassword()));
        if(!checkUniqueUsername(dto.getUsername()) || !invalidPassword(dto.getPassword()))
            return StatusReturn.INVALID2;

        dbContext.accountRepository.save(AccountMapper.toEntity(dto, dbContext));
        return StatusReturn.SUCCESS;
    }

    private boolean invalidPassword(String password) {
        boolean hadNumber = false;
        boolean hadSpecial = false;
        boolean hadAlphabetic = false;
        for(char c : password.toLowerCase().toCharArray()){
            if(!hadAlphabetic && isAlphabetic(c)) hadAlphabetic = true;
            else if(!hadNumber && isNumber(c)) hadNumber = true;
            else if(!hadSpecial && isSpecial(c)) hadSpecial = true;

            if(hadAlphabetic && hadNumber && hadSpecial) return true;
        }
        return false;
    }

    private boolean isAlphabetic(char c){
        return c >= 'a' && c <= 'z';
    }

    private boolean isNumber(char c){
        return c >= '0' && c <= '9';
    }

    private boolean isSpecial(char c){
        return !isAlphabetic(c) && !isNumber(c);
    }

    private boolean checkUniqueUsername(String username) {
        return dbContext.accountRepository.findAll().stream()
                .filter(account -> account.getUsername().toLowerCase().equals(username.toLowerCase()))
                .collect(Collectors.toList()).size() == 0;
    }

    @Override
    public StatusReturn update(AccountDTO dto){
        // check
        if(dto.getAccountId() <= 0) return StatusReturn.IS_EMPTY;
        if(!dbContext.accountRepository.existsById(dto.getAccountId())) return StatusReturn.IS_NOT_FOUND;
        if(dto.getAuthorityId() > 0 && !dbContext.authorityRepository.existsById(dto.getAuthorityId())) return StatusReturn.IS_NOT_FOUND;
        Account account = dbContext.accountRepository.findById(dto.getAccountId()).orElseThrow();
        if(dto.getUsername() != null && !dto.getUsername().equals(account.getUsername())
        && !checkUniqueUsername(dto.getUsername())) return StatusReturn.INVALID;
        if(dto.getPassword() != null && !invalidPassword(dto.getPassword())) return StatusReturn.INVALID2;
        // update
        if(dto.getFullname() != null) account.setFullname(dto.getFullname());
        if(dto.getUsername() != null) account.setUsername(dto.getUsername());
        if(dto.getPassword() != null) account.setPassword(dto.getPassword());
        if(dto.getAuthorityId() > 0) account.setAuthority(dbContext.authorityRepository
                .findById(dto.getAuthorityId()).orElseThrow());
        // save
        dbContext.accountRepository.save(account);
        return StatusReturn.SUCCESS;
    }

    @Override
    public StatusReturn delete(int accountId, boolean isDelete) {
        if (!dbContext.accountRepository.existsById(accountId))
            return StatusReturn.IS_NOT_FOUND;
        // todo delete register and article
        if (dbContext.registerRepository.counterRegisterWithAccountId(accountId) > 0) {
            if (!isDelete) return StatusReturn.INVALID2;
            dbContext.registerRepository.findAll().stream()
                    .filter(register -> register.getAccount().getAccountId() == accountId)
                    .map(register ->{
                        dbContext.registerRepository.deleteById(register.getRegisterId());
                        return null;
                    }).collect(Collectors.toList());
        }
        if (dbContext.articleRepository.counterArticleWithAccountId(accountId) > 0) {
            if (!isDelete) return StatusReturn.INVALID2;
            dbContext.articleRepository.findAll().stream()
                    .filter(article->article.getAccount().getAccountId() == accountId)
                    .map(article -> {
                        dbContext.articleRepository.deleteById(article.getArticleId());
                        return null;
                    }).collect(Collectors.toList());
        }

        dbContext.accountRepository.deleteById(accountId);
        return StatusReturn.SUCCESS;
    }
}
