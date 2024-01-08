package com.chuan.courcelts.services;

import com.chuan.courcelts.models.entities.StatusStudy;

import java.util.List;

public interface IStatusService {
    int SUCCESS = 0;
    int NOT_FOUND = 1;
    int CONFLICT = 2;
    String DANG_HOC_CHINH = "Đang Học Chính";
    String CHO_DUYET = "Chờ Duyệt";
    List<StatusStudy> findAll();

    int add(String statusName);

    int update(StatusStudy statusStudy);

    int delete(int statusId, boolean isDelete);
}
