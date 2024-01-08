package com.chuan.courcelts.models.dtos;

import com.chuan.courcelts.models.entities.CourceType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourceDTO {
    private int courceId;
    private String courceName;
    private int timer; // Thoi gian hoc
    private String intro;
    private String content;
    private float fees; // Hoc phi(cost, price)
    private int totalStudent; // So hoc vien
    private int totalSubject; // So luong mon hoc
    private String image;
    private int courceTypeId;
}
