package com.chuan.courcelts.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusStudy { // Trang thai hoc
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int statusId;
    @Column(length = 40)
    private String statusName;

    public StatusStudy(String statusName) {
        this.statusName = statusName;
    }
}
