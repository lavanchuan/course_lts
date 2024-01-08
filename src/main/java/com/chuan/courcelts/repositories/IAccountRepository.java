package com.chuan.courcelts.repositories;

import com.chuan.courcelts.models.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer> {

    @Query(value = "select count(accountId) from tb_accounts where authorityId = :authorityId",
    nativeQuery = true)
    int counterAccountWithAuthorityId(@Param("authorityId") int authorityId);
}
