package com.chuan.courcelts.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "tb_students",
uniqueConstraints = @UniqueConstraint(columnNames = {"email", "phoneNumber"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student { // Hoc vien
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;
    private String image;
    @Column(length = 50)
    private String fullname;
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private LocalDate birthday;
    @Column(length = 11)
    @NotNull
    private String phoneNumber;
    @NotNull
    @Column(length = 40)
    private String email;
    @Column(length = 50)
    private String province; // Tinh thanh
    @Column(length = 50)
    private String district; // Quan huyen
    @Column(length = 50)
    private String wards; // Phuong xa
    @Column(length = 50)
    private String houseNum; // So nha
}
