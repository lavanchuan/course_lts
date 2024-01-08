use db_cource_lts;

select * from tb_cources;

select * from tb_cources where courceName like "%ja%";

-- check register table has records contains courceId
select count(registerId) from tb_registers where courceId = 10;

-- delete records register with courceId
delete from tb_registers where courceId = 1;

-- check cource table has records contains courceTypeId
select count(courceId) from tb_cources where courceTypeId = 1;

-- delte records cource table with courceTypeId
delete from tb_cources where courceTypeId = 1;

-- check registers table has records contains studentId
select count(courceId) from tb_registers where studentId = 1;

-- delete records registers table with studentId
delete from tb_registers where studentId = 1;

-- xem so luong hoc sinh dang hoc(dang hoc chinh) cua 1 khoa
select count(distinct regis.studentId)
from tb_registers regis inner join tb_status state 
on regis.statusId = state.statusId
where statusName like "Đang Học Chính"
and courceId = 1;
