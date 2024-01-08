package com.chuan.courcelts.repositories;

import com.chuan.courcelts.models.entities.Cource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ICourceRepository extends JpaRepository<Cource, Integer> {
    @Query(value = "select * from tb_cources where courceName like %:courceName%", nativeQuery = true)
    List<Cource> findCourceListByName(@Param("courceName") String courceName);

    @Query(value = "select count(courceId) from tb_cources where courceTypeId = :courceTypeId",
    nativeQuery = true)
    int countRecords(@Param("courceTypeId") int courceTypeId);

    @Transactional
    @Modifying
    @Query(value = "delete from tb_cources where courceTypeId = :courceTypeId",
    nativeQuery = true)
    void deleteCourcesByCourceTypeId(@Param("courceTypeId") int courceTypeId);

    @Query(value = "select count(distinct regis.studentId) " +
            "from tb_registers regis " +
            "inner join tb_status state " +
            "on regis.statusId = state.statusId " +
            "where statusName like :statusName " +
            "and courceId = :courceId", nativeQuery = true)
    int counterStudent(@Param("courceId") int courceId, @Param("statusName") String statusName);

    @Query(value = "select count(distinct regis.studentId) " +
            "from tb_registers regis inner join tb_status state " +
            "on regis.statusId = state.statusId " +
            "where statusName like :statusName " +
            "and courceId = :courceId " +
            "and studentId = :studentId", nativeQuery = true)
    int counterStudentCource(@Param("courceId") int courceId, @Param("studentId") int studentId, @Param("statusName") String statusName);

    @Query(value = "select count(studentId) " +
            "from tb_registers " +
            "where courceId = :courceId " +
            "and studentId = :studentId", nativeQuery = true)
    int counterStudentCource(@Param("courceId") int courceId, @Param("studentId") int studentId);
}
