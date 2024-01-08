package com.chuan.courcelts.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account { // Tai khoan
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;
    @Column(length = 50)
    private String fullname; // Ten nguoi dung
    @Column(length = 50, unique = true)
    private String username; // Ten dang nhap
    @Column(length = 50)
    private String password;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorityId")
    private Authority authority;
}
