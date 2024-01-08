package com.chuan.courcelts.services;

import com.chuan.courcelts.models.StatusReturn;
import com.chuan.courcelts.models.dtos.AccountDTO;

import java.util.List;

public interface IAccountService {
    List<AccountDTO> findAll();

    List<AccountDTO> findByFullname(String fullname);

    List<AccountDTO> pagingFindByFullname(String fullname, int pageNum, int sizeOf);

    List<AccountDTO> pagingFindAll(int pageNum, int sizeOf);

    StatusReturn add(AccountDTO dto);

    StatusReturn update(AccountDTO dto);

    StatusReturn delete(int accountId, boolean isDelete);
}
