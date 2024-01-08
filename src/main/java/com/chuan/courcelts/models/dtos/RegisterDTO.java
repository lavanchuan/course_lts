package com.chuan.courcelts.models.dtos;

import com.chuan.courcelts.models.entities.Account;
import com.chuan.courcelts.models.entities.Cource;
import com.chuan.courcelts.models.entities.StatusStudy;
import com.chuan.courcelts.models.entities.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    private int registerId;
    private int courceId;
    private int studentId;
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private LocalDate regisDate;
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private LocalDate endDate;
    private int statusId;
    private int accountId;
}
