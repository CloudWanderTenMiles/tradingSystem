package com.frye.trading.pojo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    private String adminId;
    private String adminName;
    private String sex;
    private String adminPwd;
}
