package com.chuan.courcelts.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThemeDTO {
    private int themeId;
    private String themeName;
    private String content;
    private int articleTypeId;
}
