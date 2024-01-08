package com.chuan.courcelts.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_authorities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority { // Quyen han
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authorityId;
    @Column(length = 50)
    private String authorityName;
}
