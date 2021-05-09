package com.frye.trading.pojo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Staff {

    private String staffId;
    private String staffName;
    private String staffPwd;
    private String sex;
    private Date birth;
    private String phone;
    private String email;
    private String address;
    private String photo;
    private String role;

    public void setBirth(String birth) throws ParseException {
        if (birth.isEmpty()) {
            return;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.birth = format.parse(birth);
    }
}
