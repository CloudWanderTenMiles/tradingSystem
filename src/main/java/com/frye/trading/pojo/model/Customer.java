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
public class Customer {

    private String customerId;
    private String customerName;
    private String customerPwd;
    private String studentId;
    private String realName;
    private String sex;
    private Date birth;
    private String role;
    private String address;
    private String email;
    private String phone;
    private String photo;
    private double credit;

    /**
     * 重载setBirth函数
     * @param birth String类型
     * @throws ParseException 解析异常
     */
    public void setBirth(String birth) throws ParseException {
        if (birth.isEmpty()) {
            return;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.birth = format.parse(birth);
    }
}
