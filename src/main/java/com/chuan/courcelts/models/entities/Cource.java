package com.chuan.courcelts.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_cources")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cource { // Khoa hoc
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courceId;
    @Column(length = 50)
    private String courceName;
    @NotNull
    private int timer; // Thoi gian hoc
    @NotNull
    private String intro;
    @NotNull
    private String content;
    @NotNull
    private float fees; // Hoc phi(cost, price)
    @NotNull
    private int totalStudent; // So hoc vien
    @NotNull
    private int totalSubject; // So luong mon hoc
    @NotNull
    private String image;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courceTypeId")
    private CourceType courceType;
}
