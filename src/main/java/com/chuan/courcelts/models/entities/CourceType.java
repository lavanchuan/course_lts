package com.chuan.courcelts.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_cource_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourceType { // Loai khoa hoc
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courceTypeId;
    @Column(length = 30)
    private String courceTypeName;

    public CourceType(String name) {
        this.courceTypeName = name;
    }
}
