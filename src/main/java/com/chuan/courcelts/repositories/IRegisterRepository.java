package com.chuan.courcelts.repositories;

import com.chuan.courcelts.models.entities.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IRegisterRepository extends JpaRepository<Register, Integer> {

    @Query(value = "select count(registerId) from tb_registers where courceId = :courceId",
            nativeQuery = true)
    int countRecords(@Param("courceId") int courceId);

    @Transactional
    @Modifying
    @Query(value = "delete from tb_registers where courceId = :courceId",
            nativeQuery = true)
    void deleteRegistersByCourceId(@Param("courceId") int courceId);


    @Query(value = "select count(courceId) from tb_registers where studentId = :studentId",
            nativeQuery = true)
    int countRecordsByStudentId(@Param("studentId") int studentId);

    @Transactional
    @Modifying
    @Query(value = "delete from tb_registers where studentId = :studentId",
            nativeQuery = true)
    void deleteRegistersByStudentId(@Param("studentId") int studentId);

    @Query(value = "select count(registerId) from tb_registers where accountId = :accountId",
    nativeQuery = true)
    int counterRegisterWithAccountId(@Param("accountId") int accountId);
}
