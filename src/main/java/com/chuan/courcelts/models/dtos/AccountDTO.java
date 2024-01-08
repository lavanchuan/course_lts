package com.chuan.courcelts.models.dtos;

import com.chuan.courcelts.models.entities.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private int accountId;
    private String fullname; // Ten nguoi dung
    private String username; // Ten dang nhap
    private String password;
    private int authorityId;
}
