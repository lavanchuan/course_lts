package com.chuan.courcelts.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "tb_registers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Register {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int registerId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courceId")
    private Cource cource;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId")
    private Student student;
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private LocalDate regisDate;
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private LocalDate endDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statusId")
    private StatusStudy statusStudy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private Account account;
}
